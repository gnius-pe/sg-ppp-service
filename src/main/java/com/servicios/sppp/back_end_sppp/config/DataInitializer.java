package com.servicios.sppp.back_end_sppp.config;

import com.servicios.sppp.back_end_sppp.modelos.EstadoCartaAceptacion;
import com.servicios.sppp.back_end_sppp.repositorios.EstadoCartaAceptacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private EstadoCartaAceptacionRepositorio estadoRepositorio;

    @Override
    public void run(String... args) {
        if (estadoRepositorio.count() == 0) {
            estadoRepositorio.save(new EstadoCartaAceptacion("ENVIADO", "Carta enviada esperando revisión"));
            estadoRepositorio.save(new EstadoCartaAceptacion("CORREGIR", "Carta requiere correcciones"));
            estadoRepositorio.save(new EstadoCartaAceptacion("ACEPTADO", "Carta aceptada"));
            
            System.out.println(">> Estados de Carta de Aceptación inicializados");
        }
    }
}