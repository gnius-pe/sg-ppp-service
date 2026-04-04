package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
import com.servicios.sppp.back_end_sppp.servicios.EstadoCartaAceptacionServicioImpl;
import com.servicios.sppp.back_end_sppp.servicios.ResultadoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beta/estados-carta-aceptacion")
public class EstadoCartaAceptacionControlador {

    @Autowired
    private EstadoCartaAceptacionServicioImpl estadoCartaAceptacionServicio;

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping
    public ResponseEntity<ApiResponse<List<EstadoCartaAceptacion>>> obtenerEstados() {
        List<EstadoCartaAceptacion> estados = estadoCartaAceptacionServicio.obtenerTodo();
        return ResponseEntity.ok(ApiResponse.success(estados));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoCartaAceptacion>> obtenerEstadoPorId(@PathVariable long id) {
        EstadoCartaAceptacion estado = estadoCartaAceptacionServicio.obtenerPorId(id);
        if (estado == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Estado no encontrado"));
        }
        return ResponseEntity.ok(ApiResponse.success(estado));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping
    public ResponseEntity<ApiResponse<EstadoCartaAceptacion>> crear(@RequestBody EstadoCartaAceptacion request) {
        EstadoCartaAceptacion estado = estadoCartaAceptacionServicio.guardar(request);
        return ResponseEntity.ok(ApiResponse.success(estado, "Estado creado exitosamente"));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoCartaAceptacion>> actualizar(@PathVariable long id, @RequestBody EstadoCartaAceptacion request) {
        ResultadoOperacion<EstadoCartaAceptacion> resultado = estadoCartaAceptacionServicio.actualizar(id, request);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(resultado.getData(), resultado.getMessage()));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable long id) {
        EstadoCartaAceptacion estado = estadoCartaAceptacionServicio.obtenerPorId(id);
        if (estado == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Estado no encontrado"));
        }
        estadoCartaAceptacionServicio.eliminar(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Estado eliminado exitosamente"));
    }
}
