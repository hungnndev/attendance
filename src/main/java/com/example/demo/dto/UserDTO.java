package com.example.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String password;
    private String userFullName;
    private Set<PositionDTO> positions;
    private Set<DepartmentDTO> departments;
}