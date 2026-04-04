package com.servicios.sppp.back_end_sppp.repositorios;

import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoCartaAceptacionRepositorio extends JpaRepository<EstadoCartaAceptacion, Long> {
    Optional<EstadoCartaAceptacion> findByNombre(String nombre);
}