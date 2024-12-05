package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class ProjectSummaryDTO {
    private String name;
    private String code;
    private List<JobTypeSummaryDTO> jobTypeSummaries;
}
