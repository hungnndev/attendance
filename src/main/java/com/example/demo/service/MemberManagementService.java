package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkingTime;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.MemberManagementRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.WorkingTimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemberManagementService implements IMemberManagementService {
//
//    @Autowired
//    private Session session;

    @Autowired
    private MemberManagementRepository memberManagementRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final WorkingTimeRepository workingTimeRepository;

    public MemberManagementService(PositionRepository positionRepository, DepartmentRepository departmentRepository, WorkingTimeRepository workingTimeRepository) {
        this.positionRepository = positionRepository;
        this.departmentRepository = departmentRepository;
        this.workingTimeRepository = workingTimeRepository;
    }


    @Override
    public Iterable<User> findAll() {
        return memberManagementRepository.findAll();
    }

    @Override
    public  Optional<User> findById(Long id) {
        return memberManagementRepository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return memberManagementRepository.save(user);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        memberManagementRepository.deleteById(id);

    }
    @Transactional
    @Override
    public List<Position> getPositionByUser(Long userId) {
        if (userId!= null) {
            Optional<User> optionalUser = memberManagementRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<Position> positions = positionRepository.findByUsers(foundUser);
                log.info("Position of user {}: {}", foundUser.getUserName(), positions);
                return positions;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = memberManagementRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());

            // get position list by user and set to position for user
            List<Position> positions = positionRepository.findByUsers(user);
            Set<String> positionNames = positions.stream().map(Position::getPositionName).collect(Collectors.toSet());
            userDTO.setLocations(positionNames);

            // get position list by user and set to position for user
            List<Department> departments = departmentRepository.findByUsers(user);
            userDTO.setDepartments(departments.stream().map(Department::getDepartmentName).collect(Collectors.toSet()));

            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public List<Department>getDepartmentByUser(Long userId){
        if(userId!=null){
            Optional<User>optionalUser=memberManagementRepository.findById(userId);
            if(optionalUser.isPresent()){
                User foundUser=optionalUser.get();
                List<Department>departments=departmentRepository.findByUsers(foundUser);
                log.info("Department of user {}:{}",foundUser.getUserName(),departments);
                return departments;
            }
        }
        return Collections.emptyList();
    }
    public List<WorkingTime>getWorkingTimebyUser(Long userId){
        if(userId!=null){
            Optional<User>optionalUser=memberManagementRepository.findById(userId);
            if(optionalUser.isPresent()){
                User foundUser=optionalUser.get();
                List<WorkingTime> workingTimes= workingTimeRepository.findByUser(foundUser);
                for(WorkingTime workingTime:workingTimes){
                    log.info("WorkingTime for user{}:Date,{}Checkin_time:{},Checkout_time{},Breaktime{},Overtime{},Worktime{}",foundUser.getUserName(),
                    workingTime.getDate(), workingTime.getCheckin_time(), workingTime.getCheckout_time(),
                            workingTime.getWorktime(), workingTime.getBreaktime(), workingTime.getOvertime());

                }
                return workingTimes;
            }
        }
        return Collections.emptyList();

    }
}