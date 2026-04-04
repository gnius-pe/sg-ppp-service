package com.servicios.sppp.back_end_sppp.servicios;

import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
import com.servicios.sppp.back_end_sppp.repositorios.EstadoCartaAceptacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoCartaAceptacionServicioImpl implements IMetodosCRUD<EstadoCartaAceptacion> {

    @Autowired
    private EstadoCartaAceptacionRepositorio estadoCartaAceptacionRepositorio;

    @Override
    public List<EstadoCartaAceptacion> obtenerTodo() {
        return estadoCartaAceptacionRepositorio.findAll();
    }

    @Override
    public EstadoCartaAceptacion guardar(EstadoCartaAceptacion estadoCartaAceptacion) {
        return estadoCartaAceptacionRepositorio.save(estadoCartaAceptacion);
    }

    @Override
    public EstadoCartaAceptacion obtenerPorId(long id) {
        return estadoCartaAceptacionRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminar(long id) {
        EstadoCartaAceptacion estado = obtenerPorId(id);
        if (estado != null) {
            estadoCartaAceptacionRepositorio.delete(estado);
        }
    }

    public EstadoCartaAceptacion buscarPorNombre(String nombre) {
        return estadoCartaAceptacionRepositorio.findByNombre(nombre).orElse(null);
    }

    public ResultadoOperacion<EstadoCartaAceptacion> actualizar(long id, EstadoCartaAceptacion estadoActualizado) {
        EstadoCartaAceptacion estadoExistente = obtenerPorId(id);
        if (estadoExistente == null) {
            return ResultadoOperacion.noEncontrado("Estado no encontrado");
        }
        if (estadoActualizado.getNombre() != null) {
            estadoExistente.setNombre(estadoActualizado.getNombre());
        }
        if (estadoActualizado.getDescripcion() != null) {
            estadoExistente.setDescripcion(estadoActualizado.getDescripcion());
        }
        EstadoCartaAceptacion actualizado = estadoCartaAceptacionRepositorio.save(estadoExistente);
        return ResultadoOperacion.exito(actualizado, "Estado actualizado exitosamente");
    }
}
