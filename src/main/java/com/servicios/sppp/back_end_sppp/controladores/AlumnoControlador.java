package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.dto.AlumnoRequest;
import com.servicios.sppp.back_end_sppp.dto.AlumnoResponse;
import com.servicios.sppp.back_end_sppp.mapper.AlumnoMapper;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.servicios.AlumnoServicioImpl;
import com.servicios.sppp.back_end_sppp.servicios.ResultadoOperacion;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beta")
public class AlumnoControlador {
    @Autowired
    private AlumnoServicioImpl alumnoServicio;

    private final AlumnoMapper mapper = AlumnoMapper.INSTANCE;

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/alumnos")
    public ResponseEntity<ApiResponse<List<AlumnoResponse>>> obtenerAlumnos() {
        List<Alumno> alumnos = alumnoServicio.obtenerTodo();
        List<AlumnoResponse> response = mapper.toResponseList(alumnos);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @ApiOperation(value = "Guardar Alumno", notes = "Ejemplo: {\n  \"nombre\": \"igor\",\n  \"apellido\": \"ramos cruzado\",\n  \"email\": \"igor.ramos@unas.edu.pe\",\n  \"codigo\": \"0020190504\",\n  \"direccionActual\": \"Tingo Maria\",\n  \"numeroCelular\": \"929417416\",\n  \"password\": \"12345678\"\n}")
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping("/guardar")
    public ResponseEntity<ApiResponse<AlumnoResponse>> guardarAlumno(@RequestBody AlumnoRequest request) {
        Alumno alumno = mapper.toModel(request);
        Alumno nuevoAlumno = alumnoServicio.guardar(alumno);
        return ResponseEntity.status(201).body(ApiResponse.created(mapper.toResponse(nuevoAlumno), "Alumno creado exitosamente"));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/alumno/{id}")
    public ResponseEntity<ApiResponse<AlumnoResponse>> obtenerAlumnoId(@PathVariable long id) {
        Alumno usuarioId = alumnoServicio.obtenerPorId(id);
        if (usuarioId == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Alumno no encontrado"));
        }
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(usuarioId)));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/alumno/sesion/{email}/{password}")
    public ResponseEntity<ApiResponse<AlumnoResponse>> validarAlumno(@PathVariable String email, @PathVariable String password) {
        return alumnoServicio.iniciarSesion(email, password)
                .map(alumno -> ResponseEntity.ok(ApiResponse.success(mapper.toResponse(alumno))))
                .orElse(ResponseEntity.ok(ApiResponse.unauthorized("Credenciales inválidas")));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PutMapping("/alumno/{id}")
    public ResponseEntity<ApiResponse<AlumnoResponse>> actualizar(@PathVariable long id, @RequestBody AlumnoRequest request) {
        Alumno alumno = mapper.toModel(request);
        ResultadoOperacion<Alumno> resultado = alumnoServicio.actualizar(id, alumno);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(resultado.getData()), resultado.getMessage()));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @DeleteMapping("/alumno/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarAlumno(@PathVariable long id) {
        ResultadoOperacion<Alumno> resultado = alumnoServicio.eliminarConVerificacion(id);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(null, resultado.getMessage()));
    }
}