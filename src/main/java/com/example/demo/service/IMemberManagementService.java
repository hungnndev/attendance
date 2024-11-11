package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMemberManagementService extends IGeneralService<User>, UserDetailsService {

    List<Position> getPositionByUser(Long userId);

    List<UserDTO> getAllUser();


}