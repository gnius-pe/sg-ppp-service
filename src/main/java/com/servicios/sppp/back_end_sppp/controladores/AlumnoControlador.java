package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.dto.AlumnoRequest;
import com.servicios.sppp.back_end_sppp.dto.AlumnoResponse;
import com.servicios.sppp.back_end_sppp.mapper.AlumnoMapper;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.servicios.AlumnoServicioImpl;
import com.servicios.sppp.back_end_sppp.servicios.ResultadoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beta/alumnos")
public class AlumnoControlador {

    @Autowired
    private AlumnoServicioImpl alumnoServicio;

    private final AlumnoMapper mapper = AlumnoMapper.INSTANCE;

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping
    public ResponseEntity<ApiResponse<List<AlumnoResponse>>> obtenerAlumnos() {
        List<Alumno> alumnos = alumnoServicio.obtenerTodo();
        List<AlumnoResponse> response = mapper.toResponseList(alumnos);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AlumnoResponse>> obtenerAlumnoId(@PathVariable long id) {
        Alumno usuarioId = alumnoServicio.obtenerPorId(id);
        if (usuarioId == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Alumno no encontrado"));
        }
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(usuarioId)));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AlumnoResponse>> actualizar(@PathVariable long id, @RequestBody AlumnoRequest request) {
        Alumno alumno = mapper.toModel(request);
        ResultadoOperacion<Alumno> resultado = alumnoServicio.actualizar(id, alumno);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(resultado.getData()), resultado.getMessage()));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarAlumno(@PathVariable long id) {
        ResultadoOperacion<Alumno> resultado = alumnoServicio.eliminarConVerificacion(id);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(null, resultado.getMessage()));
    }
}