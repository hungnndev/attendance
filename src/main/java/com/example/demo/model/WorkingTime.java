package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "working_time")

public class WorkingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private LocalDate date;
    private LocalDateTime checkin_time;
    private LocalDateTime checkout_time;
    private Float breaktime;
    private LocalDateTime worktime;
    private LocalDateTime overtime;

    @OneToMany(mappedBy = "workingTime", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonManagedReference
    private User user;

}


