package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserCSVDTO {
    private Long id;
    private String userName;
    private String userFullName;
    private String positions;
    private String departments;
}
