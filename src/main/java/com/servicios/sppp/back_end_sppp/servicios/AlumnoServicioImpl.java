package com.servicios.sppp.back_end_sppp.servicios;

import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.repositorios.AlumnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServicioImpl implements IMetodosCRUD<Alumno>{

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Alumno> obtenerTodo() {
        return alumnoRepositorio.findByIsDeletedFalse();
    }

    @Override
    public Alumno guardar(Alumno alumno) {
        String passwordEncriptado = passwordEncoder.encode(alumno.getPassword());
        alumno.setPassword(passwordEncriptado);
        return alumnoRepositorio.save(alumno);
    }

    @Override
    public Alumno obtenerPorId(long id) {
        return alumnoRepositorio.findByIdAndIsDeletedFalse(id).orElse(null);
    }

    @Override
    public void eliminar(long id) {
        Alumno alumno = obtenerPorId(id);
        if (alumno != null) {
            alumno.setDeleted(true);
            alumnoRepositorio.save(alumno);
        }
    }

    public Alumno buscarPorEmail(String email) {
        return alumnoRepositorio.findByEmailAndIsDeletedFalse(email).orElse(null);
    }

    public boolean validarCredenciales(String email, String password) {
        Alumno alumno = buscarPorEmail(email);
        if (alumno == null) {
            return false;
        }
        return passwordEncoder.matches(password, alumno.getPassword());
    }

    public Optional<Alumno> iniciarSesion(String email, String password) {
        Alumno alumno = buscarPorEmail(email);
        if (alumno != null && passwordEncoder.matches(password, alumno.getPassword())) {
            return Optional.of(alumno);
        }
        return Optional.empty();
    }

    public Alumno actualizar(long id, Alumno alumnoActualizado) {
        Alumno alumnoExistente = obtenerPorId(id);
        if (alumnoExistente == null) {
            return null;
        }
        if (alumnoActualizado.getNombre() != null) {
            alumnoExistente.setNombre(alumnoActualizado.getNombre());
        }
        if (alumnoActualizado.getApellido() != null) {
            alumnoExistente.setApellido(alumnoActualizado.getApellido());
        }
        if (alumnoActualizado.getEmail() != null) {
            alumnoExistente.setEmail(alumnoActualizado.getEmail());
        }
        if (alumnoActualizado.getCodigo() != null) {
            alumnoExistente.setCodigo(alumnoActualizado.getCodigo());
        }
        if (alumnoActualizado.getDireccionActual() != null) {
            alumnoExistente.setDireccionActual(alumnoActualizado.getDireccionActual());
        }
        if (alumnoActualizado.getNumeroCelular() != null) {
            alumnoExistente.setNumeroCelular(alumnoActualizado.getNumeroCelular());
        }
        if (alumnoActualizado.getPassword() != null && !alumnoActualizado.getPassword().isEmpty()) {
            String passwordEncriptado = passwordEncoder.encode(alumnoActualizado.getPassword());
            alumnoExistente.setPassword(passwordEncriptado);
        }
        return alumnoRepositorio.save(alumnoExistente);
    }
}
