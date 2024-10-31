package com.example.demo.repository;

import com.example.demo.model.User;
import com.example.demo.model.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {
    List<WorkingTime> findByUser(User user);
}
