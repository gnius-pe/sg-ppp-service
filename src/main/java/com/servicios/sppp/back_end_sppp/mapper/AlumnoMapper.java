package com.servicios.sppp.back_end_sppp.mapper;

import com.servicios.sppp.back_end_sppp.dto.AlumnoRequest;
import com.servicios.sppp.back_end_sppp.dto.AlumnoResponse;
import com.servicios.sppp.back_end_sppp.modelos.Alumno;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    AlumnoMapper INSTANCE = Mappers.getMapper(AlumnoMapper.class);

    Alumno toModel(AlumnoRequest request);

    AlumnoResponse toResponse(Alumno alumno);

    List<AlumnoResponse> toResponseList(List<Alumno> alumnos);
}