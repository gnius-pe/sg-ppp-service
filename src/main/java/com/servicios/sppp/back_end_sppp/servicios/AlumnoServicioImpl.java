package com.servicios.sppp.back_end_sppp.servicios;

import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.repositorios.AlumnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServicioImpl implements IMetodosCRUD<Alumno>{

    @Autowired
    AlumnoRepositorio alumnoRepositorio;
    @Override
    public List<Alumno> obtenerTodo() {
        return alumnoRepositorio.findAll();
    }

    @Override
    public Alumno guardar(Alumno alumno) {
        return alumnoRepositorio.save(alumno);
    }

    @Override
    public Alumno obtenerPorId(long id) {
        return alumnoRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminar(long id) {
        alumnoRepositorio.deleteById(id);
    }
}
