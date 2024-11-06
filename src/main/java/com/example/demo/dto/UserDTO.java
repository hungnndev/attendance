package com.example.demo.dto;

import lombok.Data;

import java.util.Set;
@Data
public class UserDTO {
    private Long id;
    private String userName;
    private Set<String> locations;
    private Set<String> departments;
}
