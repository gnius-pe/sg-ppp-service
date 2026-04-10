package com.servicios.sppp.back_end_sppp.servicios;

import com.servicios.sppp.back_end_sppp.modelos.EstadoSolicitudPPP;
import com.servicios.sppp.back_end_sppp.repositorios.EstadoSolicitudPPPRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoSolicitudPPPServicioImpl implements IMetodosCRUD<EstadoSolicitudPPP> {

    @Autowired
    private EstadoSolicitudPPPRepositorio estadoSolicitudPPPRepositorio;

    @Override
    public List<EstadoSolicitudPPP> obtenerTodo() {
        return estadoSolicitudPPPRepositorio.findAll();
    }

    @Override
    public EstadoSolicitudPPP guardar(EstadoSolicitudPPP estadoSolicitudPPP) {
        return estadoSolicitudPPPRepositorio.save(estadoSolicitudPPP);
    }

    @Override
    public EstadoSolicitudPPP obtenerPorId(long id) {
        return estadoSolicitudPPPRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminar(long id) {
        EstadoSolicitudPPP estado = obtenerPorId(id);
        if (estado != null) {
            estadoSolicitudPPPRepositorio.delete(estado);
        }
    }

    public EstadoSolicitudPPP buscarPorNombre(String nombre) {
        return estadoSolicitudPPPRepositorio.findByNombre(nombre).orElse(null);
    }

    public ResultadoOperacion<EstadoSolicitudPPP> actualizar(long id, EstadoSolicitudPPP estadoActualizado) {
        EstadoSolicitudPPP estadoExistente = obtenerPorId(id);
        if (estadoExistente == null) {
            return ResultadoOperacion.noEncontrado("Estado no encontrado");
        }
        if (estadoActualizado.getNombre() != null) {
            estadoExistente.setNombre(estadoActualizado.getNombre());
        }
        if (estadoActualizado.getDescripcion() != null) {
            estadoExistente.setDescripcion(estadoActualizado.getDescripcion());
        }
        EstadoSolicitudPPP actualizado = estadoSolicitudPPPRepositorio.save(estadoExistente);
        return ResultadoOperacion.exito(actualizado, "Estado actualizado exitosamente");
    }
}
