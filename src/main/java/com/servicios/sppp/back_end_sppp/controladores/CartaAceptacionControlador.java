package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionRequest;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionResponse;
import com.servicios.sppp.back_end_sppp.mapper.CartaAceptacionMapper;
import com.servicios.sppp.back_end_sppp.modelos.CartaACeptacion;
import com.servicios.sppp.back_end_sppp.servicios.ArchivoServicio;
import com.servicios.sppp.back_end_sppp.servicios.CartaAceptacionImpl;
import com.servicios.sppp.back_end_sppp.servicios.ResultadoOperacion;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/beta/cartas-aceptacion")
public class CartaAceptacionControlador {

    @Autowired
    private CartaAceptacionImpl servicioCartaAceptacion;

    @Autowired
    private ArchivoServicio archivoServicio;

    private final CartaAceptacionMapper mapper = CartaAceptacionMapper.INSTANCE;

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping
    public ResponseEntity<ApiResponse<List<CartaAceptacionResponse>>> obtenerTodo() {
        List<CartaACeptacion> cartas = servicioCartaAceptacion.obtenerTodo();
        List<CartaAceptacionResponse> response = mapper.toResponseList(cartas);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartaAceptacionResponse>> obtenerPorId(@PathVariable long id) {
        CartaACeptacion carta = servicioCartaAceptacion.obtenerPorId(id);
        if (carta == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Carta de aceptación no encontrada"));
        }
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(carta)));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/alumno/{idAlumno}")
    public ResponseEntity<ApiResponse<List<CartaAceptacionResponse>>> obtenerPorAlumno(@PathVariable long idAlumno) {
        List<CartaACeptacion> cartas = servicioCartaAceptacion.obtenerPorAlumno(idAlumno);
        List<CartaAceptacionResponse> response = mapper.toResponseList(cartas);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping
    public ResponseEntity<ApiResponse<CartaAceptacionResponse>> guardar(@RequestBody CartaAceptacionRequest request) {
        ResultadoOperacion<CartaACeptacion> resultado = servicioCartaAceptacion.guardarDesdeRequest(request);
        
        if (!resultado.isSuccess()) {
            return ResponseEntity.ok(ApiResponse.badRequest(resultado.getMessage()));
        }
        return ResponseEntity.status(201).body(ApiResponse.created(mapper.toResponse(resultado.getData()), resultado.getMessage()));
    }

    @ApiOperation(value = "Subir archivo PDF", notes = "Sube un archivo PDF a MinIO y opcionalmente lo asocia a una carta de aceptación")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Archivo subido exitosamente"),
        @ApiResponse(code = 400, message = "Error al subir archivo")
    })
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping("/subir-archivo")
    public ResponseEntity<ApiResponse<String>> subirArchivo(
            @ApiParam(value = "Archivo PDF a subir", required = true) @RequestParam("archivo") MultipartFile archivo,
            @ApiParam(value = "ID de la carta de aceptación (opcional)") @RequestParam(value = "idCarta", required = false) Long idCarta) {
        try {
            String url = archivoServicio.subirArchivo(archivo, "cartas-aceptacion");
            
            if (idCarta != null) {
                CartaACeptacion carta = servicioCartaAceptacion.obtenerPorId(idCarta);
                if (carta != null) {
                    carta.setUrl(url);
                    servicioCartaAceptacion.guardar(carta);
                }
            }
            
            return ResponseEntity.ok(ApiResponse.success(url, "Archivo subido exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.badRequest("Error al subir archivo: " + e.getMessage()));
        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CartaAceptacionResponse>> actualizar(@PathVariable long id, @RequestBody CartaAceptacionRequest request) {
        ResultadoOperacion<CartaACeptacion> resultado = servicioCartaAceptacion.actualizarDesdeRequest(id, request);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        if (!resultado.isSuccess()) {
            return ResponseEntity.ok(ApiResponse.badRequest(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(resultado.getData()), resultado.getMessage()));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable long id) {
        ResultadoOperacion<CartaACeptacion> resultado = servicioCartaAceptacion.eliminarConVerificacion(id);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(null, resultado.getMessage()));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<CartaAceptacionResponse>> actualizarEstado(@PathVariable long id, @RequestParam String estado) {
        ResultadoOperacion<CartaACeptacion> resultado = servicioCartaAceptacion.actualizarEstado(id, estado);
        
        if (!resultado.isEncontrado()) {
            return ResponseEntity.ok(ApiResponse.notFound(resultado.getMessage()));
        }
        if (!resultado.isSuccess()) {
            return ResponseEntity.ok(ApiResponse.badRequest(resultado.getMessage()));
        }
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(resultado.getData()), resultado.getMessage()));
    }
}