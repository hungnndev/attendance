package com.example.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    @NotNull
    private Long id;

    private String userName;

    @NotNull
    private String password;
    private String userFullName;

    @Valid
    private Set<PositionDTO> positions;

    @Valid
    private Set<DepartmentDTO> departments;
}