package com.servicios.sppp.back_end_sppp.servicios;

import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.modelos.CartaACeptacion;
import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
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