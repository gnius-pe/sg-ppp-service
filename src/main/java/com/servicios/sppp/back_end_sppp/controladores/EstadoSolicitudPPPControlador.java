package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.modelos.EstadoSolicitudPPP;
import com.servicios.sppp.back_end_sppp.servicios.EstadoSolicitudPPPServicioImpl;
import com.servicios.sppp.back_end_sppp.servicios.ResultadoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beta/estados-solicitud-ppp")
public class EstadoSolicitudPPPControlador {

    @Autowired
    private EstadoSolicitudPPPServicioImpl estadoSolicitudPPPServicio;

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping
    public ResponseEntity<ApiResponse<List<EstadoSolicitudPPP>>> obtenerEstados() {
        List<EstadoSolicitudPPP> estados = estadoSolicitudPPPServicio.obtenerTodo();
        return ResponseEntity.ok(ApiResponse.success(estados));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoSolicitudPPP>> obtenerEstadoPorId(@PathVariable long id) {
        EstadoSolicitudPPP estado = estadoSolicitudPPPServicio.obtenerPorId(id);
        if (estado == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Estado no encontrado"));
        }
        return ResponseEntity.ok(ApiResponse.success(estado));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping
    public ResponseEntity<ApiResponse<EstadoSolicitudPPP>> crear(@RequestBody EstadoSolicitudPPP request) {
        EstadoSolicitudPPP estado = estadoSolicitudPPPServicio.guardar(request);
        return ResponseEntity.ok(ApiResponse.success(estado, "Estado creado exitosamente"));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstadoSolicitudPPP>> actualizar(@PathVariable long id, @RequestBody EstadoSolicitudPPP request) {
        ResultadoOperacion<EstadoSolicitudPPP> resultado = estadoSolicitudPPPServicio.actualizar(id, request);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(resultado.getData(), resultado.getMessage()));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable long id) {
        EstadoSolicitudPPP estado = estadoSolicitudPPPServicio.obtenerPorId(id);
        if (estado == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Estado no encontrado"));
        }
        estadoSolicitudPPPServicio.eliminar(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Estado eliminado exitosamente"));
    }
}
