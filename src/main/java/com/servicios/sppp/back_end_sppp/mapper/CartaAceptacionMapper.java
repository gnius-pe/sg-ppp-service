package com.servicios.sppp.back_end_sppp.mapper;

import com.servicios.sppp.back_end_sppp.dto.AlumnoResponse;
import com.servicios.sppp.back_end_sppp.dto.CartaAceptacionResponse;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import com.servicios.sppp.back_end_sppp.modelos.CartaACeptacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartaAceptacionMapper {

    CartaAceptacionMapper INSTANCE = Mappers.getMapper(CartaAceptacionMapper.class);

    @Mapping(target = "alumno", source = "alumno")
    CartaAceptacionResponse toResponse(CartaACeptacion carta);

    List<CartaAceptacionResponse> toResponseList(List<CartaACeptacion> cartas);
}