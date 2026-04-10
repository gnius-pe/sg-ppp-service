package com.servicios.sppp.back_end_sppp.config;

import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
import com.servicios.sppp.back_end_sppp.modelos.EstadoSolicitudPPP;
import com.servicios.sppp.back_end_sppp.repositorios.EstadoCartaAceptacionRepositorio;
import com.servicios.sppp.back_end_sppp.repositorios.EstadoSolicitudPPPRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EstadoCartaAceptacionRepositorio estadoCartaRepositorio;

    @Autowired
    private EstadoSolicitudPPPRepositorio estadoSolicitudRepositorio;

    @Override
    public void run(String... args) {
        if (estadoCartaRepositorio.count() == 0) {
            estadoCartaRepositorio.save(new EstadoCartaAceptacion("ENVIADO", "Carta enviada esperando revisión"));
            estadoCartaRepositorio.save(new EstadoCartaAceptacion("CORREGIR", "Carta requiere correcciones"));
            estadoCartaRepositorio.save(new EstadoCartaAceptacion("ACEPTADO", "Carta aceptada"));
            
            System.out.println(">> Estados de Carta de Aceptación inicializados");
        }

        if (estadoSolicitudRepositorio.count() == 0) {
            estadoSolicitudRepositorio.save(new EstadoSolicitudPPP("PENDIENTE", "Solicitud pendiente de revisión"));
            estadoSolicitudRepositorio.save(new EstadoSolicitudPPP("APROBADO", "Solicitud aprobada"));
            estadoSolicitudRepositorio.save(new EstadoSolicitudPPP("RECHAZADO", "Solicitud rechazada"));
            
            System.out.println(">> Estados de Solicitud PPP inicializados");
        }
    }
}
