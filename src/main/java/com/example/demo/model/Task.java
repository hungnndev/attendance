package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "task")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime date;

    private float totalTime;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "working_time_id")
    @JsonBackReference
    private WorkingTime workingTime;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JoinColumn(name = "jobtype_id")
    @JsonBackReference
    private JobType jobtype;

}