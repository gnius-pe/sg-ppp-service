package com.servicios.sppp.back_end_sppp.repositorios;

import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepositorio extends JpaRepository<Alumno,Long> {
    Optional<Alumno> findByEmailAndIsDeletedFalse(String email);
    List<Alumno> findByIsDeletedFalse();
    Optional<Alumno> findByIdAndIsDeletedFalse(Long id);
}
