package com.servicios.sppp.back_end_sppp.servicios;

import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionRequest;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.modelos.CartaACeptacion;
import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
import com.servicios.sppp.back_end_sppp.repositorios.AlumnoRepositorio;
import com.servicios.sppp.back_end_sppp.repositorios.CartaAceptacionRepositorio;
import com.servicios.sppp.back_end_sppp.repositorios.EstadoCartaAceptacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaAceptacionImpl implements IMetodosCRUD<CartaACeptacion>{

    @Autowired
    private CartaAceptacionRepositorio cartaAceptacionRepositorio;

    @Autowired
    private EstadoCartaAceptacionRepositorio estadoCartaAceptacionRepositorio;

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    @Override
    public List<CartaACeptacion> obtenerTodo() {
        return cartaAceptacionRepositorio.findByIsDeletedFalse();
    }

    @Override
    public CartaACeptacion guardar(CartaACeptacion cartaACeptacion) {
        return cartaAceptacionRepositorio.save(cartaACeptacion);
    }

    @Override
    public CartaACeptacion obtenerPorId(long id) {
        return cartaAceptacionRepositorio.findByIdAndIsDeletedFalse(id).orElse(null);
    }

    @Override
    public void eliminar(long id) {
        CartaACeptacion carta = obtenerPorId(id);
        if (carta != null) {
            carta.setDeleted(true);
            cartaAceptacionRepositorio.save(carta);
        }
    }

    public List<CartaACeptacion> obtenerPorAlumno(Long idAlumno) {
        return cartaAceptacionRepositorio.findByAlumnoIdAndIsDeletedFalse(idAlumno);
    }

    public Optional<EstadoCartaAceptacion> obtenerEstadoPorNombre(String nombre) {
        return estadoCartaAceptacionRepositorio.findByNombre(nombre);
    }

    public ResultadoOperacion<CartaACeptacion> guardarDesdeRequest(CartaAceptacionRequest request) {
        Alumno alumno = null;
        if (request.getIdAlumno() != null) {
            alumno = alumnoRepositorio.findByIdAndIsDeletedFalse(request.getIdAlumno()).orElse(null);
            if (alumno == null) {
                return ResultadoOperacion.error("Alumno no encontrado");
            }
        }

        EstadoCartaAceptacion estado = null;
        if (request.getIdEstado() != null) {
            estado = estadoCartaAceptacionRepositorio.findById(request.getIdEstado()).orElse(null);
            if (estado == null) {
                return ResultadoOperacion.error("Estado no encontrado");
            }
        }

        CartaACeptacion carta = new CartaACeptacion();
        carta.setTitulo(request.getTitulo());
        carta.setDescripcion(request.getDescripcion());
        carta.setFechaEntrega(request.getFechaEntrega());
        carta.setUrl(request.getUrl());
        carta.setAlumno(alumno);
        carta.setEstado(estado);

        CartaACeptacion guardada = guardar(carta);
        return ResultadoOperacion.exito(guardada, "Carta de aceptación creada exitosamente");
    }

    public ResultadoOperacion<CartaACeptacion> actualizarDesdeRequest(long id, CartaAceptacionRequest request) {
        CartaACeptacion cartaExistente = obtenerPorId(id);
        if (cartaExistente == null) {
            return ResultadoOperacion.noEncontrado("Carta de aceptación no encontrada");
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
            EstadoCartaAceptacion estado = estadoCartaAceptacionRepositorio.findById(request.getIdEstado()).orElse(null);
            if (estado != null) cartaExistente.setEstado(estado);
        }

        CartaACeptacion actualizada = guardar(cartaExistente);
        return ResultadoOperacion.exito(actualizada, "Carta de aceptación actualizada exitosamente");
    }

    public ResultadoOperacion<CartaACeptacion> actualizarEstado(long id, String nombreEstado) {
        CartaACeptacion carta = obtenerPorId(id);
        if (carta == null) {
            return ResultadoOperacion.noEncontrado("Carta de aceptación no encontrada");
        }

        Optional<EstadoCartaAceptacion> estadoOpt = obtenerEstadoPorNombre(nombreEstado);
        if (estadoOpt.isEmpty()) {
            return ResultadoOperacion.error("Estado no válido: " + nombreEstado);
        }

        carta.setEstado(estadoOpt.get());
        CartaACeptacion actualizada = cartaAceptacionRepositorio.save(carta);
        return ResultadoOperacion.exito(actualizada, "Estado actualizado exitosamente");
    }

    public ResultadoOperacion<CartaACeptacion> eliminarConVerificacion(long id) {
        CartaACeptacion carta = obtenerPorId(id);
        if (carta == null) {
            return ResultadoOperacion.noEncontrado("Carta de aceptación no encontrada");
        }
        eliminar(id);
        return ResultadoOperacion.exito(null, "Carta de aceptación eliminada exitosamente");
    }
}