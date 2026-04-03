package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.dto.AlumnoRequest;
import com.servicios.sppp.back_end_sppp.dto.AlumnoResponse;
import com.servicios.sppp.back_end_sppp.dto.CredencialesRequest;
import com.servicios.sppp.back_end_sppp.mapper.AlumnoMapper;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.servicios.AlumnoServicioImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beta/auth")
public class AuthControlador {

    @Autowired
    private AlumnoServicioImpl alumnoServicio;

    private final AlumnoMapper mapper = AlumnoMapper.INSTANCE;

    @ApiOperation(value = "Iniciar Sesión", notes = "Recibe email y password y retorna el alumno si las credenciales son válidas")
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AlumnoResponse>> iniciarSesion(@RequestBody CredencialesRequest credenciales) {
        return alumnoServicio.iniciarSesion(credenciales.getEmail(), credenciales.getPassword())
                .map(alumno -> ResponseEntity.ok(ApiResponse.success(mapper.toResponse(alumno), "Login exitoso")))
                .orElse(ResponseEntity.ok(ApiResponse.unauthorized("Credenciales inválidas")));
    }

    @ApiOperation(value = "Registrar Alumno", notes = "Ejemplo: {\n  \"nombre\": \"igor\",\n  \"apellido\": \"ramos\",\n  \"email\": \"igor.ramos@unas.edu.pe\",\n  \"codigo\": \"0020190504\",\n  \"direccionActual\": \"Tingo Maria\",\n  \"numeroCelular\": \"929417416\",\n  \"password\": \"12345678\"\n}")
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AlumnoResponse>> registrar(@RequestBody AlumnoRequest request) {
        Alumno alumno = mapper.toModel(request);
        Alumno nuevoAlumno = alumnoServicio.guardar(alumno);
        return ResponseEntity.status(201).body(ApiResponse.created(mapper.toResponse(nuevoAlumno), "Alumno registrado exitosamente"));
    }
}