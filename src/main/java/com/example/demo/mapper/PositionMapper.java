package com.example.demo.mapper;

import com.example.demo.dto.response.PositionResponse;
import com.example.demo.model.Position;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionResponse toPositionResponse(Position Position);
}
