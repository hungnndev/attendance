package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "jobtype")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTypeName;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    @ManyToMany(mappedBy = "jobtypes")
    @JsonBackReference
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobtype", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Task> tasks;
}