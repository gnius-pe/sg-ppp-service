package com.servicios.sppp.back_end_sppp.repositorios;

import com.servicios.sppp.back_end_sppp.modelos.EstadoSolicitudPPP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoSolicitudPPPRepositorio extends JpaRepository<EstadoSolicitudPPP, Long> {
    Optional<EstadoSolicitudPPP> findByNombre(String nombre);
}
