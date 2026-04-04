package com.servicios.sppp.back_end_sppp.controladores;

import com.servicios.sppp.back_end_sppp.config.ApiResponse;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionRequest;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionResponse;
import com.servicios.sppp.back_end_sppp.mapper.CartaAceptacionMapper;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.modelos.CartaACeptacion;
import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
import com.servicios.sppp.back_end_sppp.repositorios.AlumnoRepositorio;
import com.servicios.sppp.back_end_sppp.repositorios.EstadoCartaAceptacionRepositorio;
import com.servicios.sppp.back_end_sppp.servicios.CartaAceptacionImpl;
import com.servicios.sppp.back_end_sppp.servicios.ResultadoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beta/cartas-aceptacion")
public class CartaAceptacionControlador {

    @Autowired
    private CartaAceptacionImpl servicioCartaAceptacion;

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    @Autowired
    private EstadoCartaAceptacionRepositorio estadoRepositorio;

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
        CartaACeptacion carta = new CartaACeptacion();
        carta.setTitulo(request.getTitulo());
        carta.setDescripcion(request.getDescripcion());
        carta.setFechaEntrega(request.getFechaEntrega());
        carta.setUrl(request.getUrl());

        if (request.getIdAlumno() != null) {
            Alumno alumno = alumnoRepositorio.findByIdAndIsDeletedFalse(request.getIdAlumno()).orElse(null);
            if (alumno == null) {
                return ResponseEntity.ok(ApiResponse.badRequest("Alumno no encontrado"));
            }
            carta.setAlumno(alumno);
        }

        if (request.getIdEstado() != null) {
            EstadoCartaAceptacion estado = estadoRepositorio.findById(request.getIdEstado()).orElse(null);
            if (estado == null) {
                return ResponseEntity.ok(ApiResponse.badRequest("Estado no encontrado"));
            }
            carta.setEstado(estado);
        }

        CartaACeptacion nuevaCarta = servicioCartaAceptacion.guardar(carta);
        return ResponseEntity.status(201).body(ApiResponse.created(mapper.toResponse(nuevaCarta), "Carta de aceptación creada exitosamente"));
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5173","https://sysppp.netlify.app/"})
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CartaAceptacionResponse>> actualizar(@PathVariable long id, @RequestBody CartaAceptacionRequest request) {
        CartaACeptacion cartaExistente = servicioCartaAceptacion.obtenerPorId(id);
        if (cartaExistente == null) {
            return ResponseEntity.ok(ApiResponse.notFound("Carta de aceptación no encontrada"));
        }

        if (request.getTitulo() != null) cartaExistente.setTitulo(request.getTitulo());
        if (request.getDescripcion() != null) cartaExistente.setDescripcion(request.getDescripcion());
        if (request.getFechaEntrega() != null) cartaExistente.setFechaEntrega(request.getFechaEntrega());
        if (request.getUrl() != null) cartaExistente.setUrl(request.getUrl());

        if (request.getIdAlumno() != null) {
            Alumno alumno = alumnoRepositorio.findByIdAndIsDeletedFalse(request.getIdAlumno()).orElse(null);
            if (alumno != null) cartaExistente.setAlumno(alumno);
        }

        if (request.getIdEstado() != null) {
            EstadoCartaAceptacion estado = estadoRepositorio.findById(request.getIdEstado()).orElse(null);
            if (estado != null) cartaExistente.setEstado(estado);
        }

        CartaACeptacion actualizada = servicioCartaAceptacion.guardar(cartaExistente);
        return ResponseEntity.ok(ApiResponse.success(mapper.toResponse(actualizada), "Carta de aceptación actualizada exitosamente"));
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