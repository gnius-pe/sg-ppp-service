package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.dto.AlumnoRequest;
import com.servicios.sppp.back_end_sppp.dto.AlumnoResponse;
import com.servicios.sppp.back_end_sppp.mapper.AlumnoMapper;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.servicios.UsuarioServicioImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/beta")
public class AlumnoControlador {
    @Autowired
    UsuarioServicioImpl usuarioServicio;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final AlumnoMapper mapper = AlumnoMapper.INSTANCE;

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/alumnos")
    public List<AlumnoResponse> obtenerAlumnos(){
        List<Alumno> alumnos = usuarioServicio.obtenerTodo();
        return mapper.toResponseList(alumnos);
    }

    @ApiOperation(value = "Guardar Alumno", notes = "Ejemplo: {\n  \"nombre\": \"igor\",\n  \"apellido\": \"ramos cruzado\",\n  \"email\": \"igor.ramos@unas.edu.pe\",\n  \"codigo\": \"0020190504\",\n  \"direccionActual\": \"Tingo Maria\",\n  \"numeroCelular\": \"929417416\",\n  \"password\": \"12345678\"\n}")
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping("/guardar")
    public ResponseEntity<AlumnoResponse> guardarAlumno(@RequestBody AlumnoRequest request){
        Alumno alumno = mapper.toModel(request);
        String passwordEncriptado = passwordEncoder.encode(alumno.getPassword());
        alumno.setPassword(passwordEncriptado);
        Alumno nuevoAlumno = usuarioServicio.guardar(alumno);
        return new ResponseEntity<>(mapper.toResponse(nuevoAlumno), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/alumno/{id}")
    public ResponseEntity<AlumnoResponse> obtenerAlumnoId(@PathVariable long id){
        Alumno usuarioId = usuarioServicio.obtenerPorId(id);
        return ResponseEntity.ok(mapper.toResponse(usuarioId));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/alumno/sesion/{email}/{password}")
    public ResponseEntity<AlumnoResponse> validarAlumno(@PathVariable String email, @PathVariable String password){
        List<Alumno> alumnos = usuarioServicio.obtenerTodo();
        for(Alumno alumno: alumnos){
            if(alumno.getEmail().equals(email) && passwordEncoder.matches(password, alumno.getPassword())){
                return ResponseEntity.ok(mapper.toResponse(alumno));
            }
        }
        return ResponseEntity.ok(null);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PutMapping("/alumno/{id}")
    public ResponseEntity<AlumnoResponse> actualizar(@PathVariable long id, @RequestBody AlumnoRequest request){
        Alumno usuarioId = usuarioServicio.obtenerPorId(id);
        usuarioId.setNombre(request.getNombre());
        usuarioId.setApellido(request.getApellido());
        usuarioId.setEmail(request.getEmail());
        usuarioId.setCodigo(request.getCodigo());
        usuarioId.setDireccionActual(request.getDireccionActual());
        usuarioId.setNumeroCelular(request.getNumeroCelular());
        usuarioId.setPassword(passwordEncoder.encode(request.getPassword()));
        Alumno usuarioActualizado = usuarioServicio.guardar(usuarioId);
        return new ResponseEntity<>(mapper.toResponse(usuarioActualizado), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @DeleteMapping("/alumno/{id}")
    public ResponseEntity<HashMap<String,Boolean>> eliminarAlumno(@PathVariable long id){
        this.usuarioServicio.eliminar(id);
        HashMap<String, Boolean> estadoUsuarioEliminado = new HashMap<>();
        estadoUsuarioEliminado.put("eliminado",true);
        return ResponseEntity.ok(estadoUsuarioEliminado);
    }
}