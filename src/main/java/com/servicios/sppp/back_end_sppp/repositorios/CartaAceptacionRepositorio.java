package com.servicios.sppp.back_end_sppp.repositorios;

import com.servicios.sppp.back_end_sppp.modelos.CartaACeptacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaAceptacionRepositorio extends JpaRepository<CartaACeptacion, Long> {
    List<CartaACeptacion> findByIsDeletedFalse();
    Optional<CartaACeptacion> findByIdAndIsDeletedFalse(Long id);
    List<CartaACeptacion> findByAlumnoIdAndIsDeletedFalse(Long idAlumno);
}