package com.example.demo.service;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserExcelDTO;
import com.example.demo.model.*;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.MemberManagementRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.WorkingTimeRepository;
import com.example.demo.security.MyUserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberManagementService implements IMemberManagementService {

    private final MemberManagementRepository memberManagementRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final WorkingTimeRepository workingTimeRepository;

    @Override
    public Iterable<User> findAll() {
        return memberManagementRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return memberManagementRepository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return memberManagementRepository.save(user);
    }

    @Override
    public void remove(Long id) {

    }

    @Transactional
    public void delete(User user) {
//        List<WorkingTime> workingTimes = workingTimeRepository.findByUser(user);
//        workingTimeRepository.deleteAll(workingTimes);
        memberManagementRepository.deleteById(user.getId());
    }

    @Override
    public List<Position> getPositionByUser(Long userId) {
        if (userId != null) {
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
            userDTO.setUserFullName(user.getUserFullName());
            userDTO.setId(user.getId());

            //get position list by user and set to position for user
            List<Position> positions = positionRepository.findByUsers(user);
            Set<PositionDTO> positionDTOS = new HashSet<>();
            for (Position position : positions) {
                PositionDTO positionDTO = new PositionDTO();
                positionDTO.setId(position.getId());
                positionDTO.setPositionName(position.getPositionName());
                positionDTOS.add(positionDTO);
            }
            userDTO.setPositions(positionDTOS);

            //get department list by user and set to department for user
            List<Department> departments = departmentRepository.findByUsers(user);
            Set<DepartmentDTO> departmentDTOS = new HashSet<>();
            for (Department department : departments) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentName(department.getDepartmentName());
                departmentDTOS.add(departmentDTO);
            }
            userDTO.setDepartments(departmentDTOS);

            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public List<Department> getDepartmentByUser(Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = memberManagementRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<Department> departments = departmentRepository.findByUsers(foundUser);
                log.info("Department of user {}:{}", foundUser.getUserName(), departments);
                return departments;
            }
        }
        return Collections.emptyList();
    }

    public List<WorkingTime> getWorkingTimebyUser(Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = memberManagementRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<WorkingTime> workingTimes = workingTimeRepository.findByUser(foundUser);
                for (WorkingTime workingTime : workingTimes) {
                    log.info("WorkingTime for user{}:Date,{}Checkin_time:{},Checkout_time{},Breaktime{},Overtime{},Worktime{}", foundUser.getUserName(), workingTime.getDate(), workingTime.getCheckin_time(), workingTime.getCheckout_time(), workingTime.getWorktime(), workingTime.getBreaktime(), workingTime.getOvertime());

                }
                return workingTimes;
            }
        }
        return Collections.emptyList();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = memberManagementRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User is not exist!");
        }
        return new MyUserPrincipal(user.get());
    }

    public List<UserExcelDTO> getUsersExcel() {
        List<User> users = memberManagementRepository.findAll();
        List<UserExcelDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserExcelDTO userDTO = new UserExcelDTO();
            userDTO.setUserName(user.getUserName());

            userDTO.setUserFullName(user.getUserFullName());


            //get position list by user and set to position for user
            List<Position> positions = positionRepository.findByUsers(user);
            Set<String> positionNames = positions.stream().map(Position::getPositionName).collect(Collectors.toSet());
            userDTO.setPositions(String.join(", ", positionNames));

            //get department list by user and set to department for user
            List<Department> departments = departmentRepository.findByUsers(user);
            Set<String> departmentsNames = departments.stream().map(Department::getDepartmentName).collect(Collectors.toSet());
            userDTO.setDepartments(String.join(", ", departmentsNames));

            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

}