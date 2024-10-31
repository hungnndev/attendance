package com.example.demo.service;

import com.example.demo.model.Position;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMemberManagementService extends IGeneralService<User> {

    List<Position> getPositionByUser(Long userId);
}
