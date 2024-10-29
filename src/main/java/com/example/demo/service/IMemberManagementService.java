package com.example.demo.service;

import com.example.demo.model.Position;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IMemberManagementService extends IGeneralService<User> {

    Set<Position> getPositionByUser(Long userId);
}