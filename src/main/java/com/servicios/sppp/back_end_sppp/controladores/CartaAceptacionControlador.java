package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionCompletaRequest;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionRequest;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionResponse;
import com.servicios.sppp.back_end_sppp.mapper.CartaAceptacionMapper;
import com.servicios.sppp.back_end_sppp.modelos.CartaACeptacion;
import com.servicios.sppp.back_end_sppp.servicios.ArchivoServicio;
import com.servicios.sppp.back_end_sppp.servicios.CartaAceptacionImpl;
import com.servicios.sppp.back_end_sppp.servicios.ResultadoOperacion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Crear carta con archivo", description = "Crea una carta de aceptación junto con su archivo PDF en una sola llamada")
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping(value = "/crear-con-archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<CartaAceptacionResponse>> crearConArchivo(
            @Parameter(description = "Título de la carta") @RequestParam("titulo") String titulo,
            @Parameter(description = "Descripción de la carta") @RequestParam(value = "descripcion", required = false) String descripcion,
            @Parameter(description = "ID del alumno") @RequestParam(value = "idAlumno", required = false) Long idAlumno,
            @Parameter(description = "Nombre del estado") @RequestParam(value = "nombreEstado", required = false) String nombreEstado,
            @Parameter(description = "Archivo PDF") @RequestParam(value = "archivo", required = false) MultipartFile archivo) {
        
        CartaAceptacionCompletaRequest request = new CartaAceptacionCompletaRequest();
        request.setTitulo(titulo);
        request.setDescripcion(descripcion);
        request.setIdAlumno(idAlumno);
        request.setNombreEstado(nombreEstado);
        
        ResultadoOperacion<CartaACeptacion> resultado = servicioCartaAceptacion.guardarConArchivo(request, archivo);
        
        if (!resultado.isSuccess()) {
            return ResponseEntity.ok(ApiResponse.badRequest(resultado.getMessage()));
        }
        return ResponseEntity.status(201).body(ApiResponse.created(mapper.toResponse(resultado.getData()), resultado.getMessage()));
    }

    @Operation(summary = "Subir archivo PDF", description = "Sube un archivo PDF a MinIO y opcionalmente lo asocia a una carta de aceptación")
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PostMapping(value = "/subir-archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ArchivoServicio.ResultadoArchivo>> subirArchivo(
            @Parameter(description = "Archivo PDF a subir", required = true) @RequestParam("archivo") MultipartFile archivo,
            @Parameter(description = "ID de la carta de aceptación (opcional)") @RequestParam(value = "idCarta", required = false) Long idCarta) {
        try {
            ArchivoServicio.ResultadoArchivo resultado = archivoServicio.subirArchivo(archivo, "cartas-aceptacion");
            
            if (idCarta != null) {
                CartaACeptacion carta = servicioCartaAceptacion.obtenerPorId(idCarta);
                if (carta != null) {
                    carta.setRutaArchivo(resultado.getRuta());
                    carta.setUrl(resultado.getUrl());

                    servicioCartaAceptacion.guardar(carta);
                }
            }
            
            return ResponseEntity.ok(ApiResponse.success(resultado, "Archivo subido exitosamente"));
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

    @Operation(summary = "Obtener URL para visualizar PDF", description = "Genera una URL pre-firmada válida por 7 días para visualizar el archivo PDF")
    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @GetMapping("/{id}/visualizar-pdf")
    public ResponseEntity<ApiResponse<String>> obtenerUrlVisualizacion(@PathVariable long id) {
        CartaACeptacion carta = servicioCartaAceptacion.obtenerPorId(id);
        if (carta == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Carta de aceptación no encontrada"));
        }
        if (carta.getRutaArchivo() == null || carta.getRutaArchivo().isEmpty()) {
            return ResponseEntity.ok(ApiResponse.badRequest("La carta no tiene archivo asociado"));
        }
        
        try {
            String urlPrefirmada = archivoServicio.generarUrlPrefirmada(carta.getRutaArchivo());
            return ResponseEntity.ok(ApiResponse.success(urlPrefirmada, "URL para visualizar PDF"));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.badRequest("Error al generar URL: " + e.getMessage()));
        }
    }
}