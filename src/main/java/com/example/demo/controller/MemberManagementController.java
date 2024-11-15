package com.example.demo.controller;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserExcelDTO;
import com.example.demo.excel.UserCSVExporter;
import com.example.demo.excel.UserExcelExporter;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkingTime;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.WorkingTimeRepository;
import com.example.demo.service.MemberManagementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user/member")
public class MemberManagementController {
    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private WorkingTimeRepository workingTimeRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(memberManagementService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getAllUserById(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("getPosition/{id}")
    public ResponseEntity<?> getPosition(@PathVariable Long id) {
        List<Position> positions = memberManagementService.getPositionByUser(id);
        return ResponseEntity.ok().body(positions);
    }

    @GetMapping("getDepartment/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id) {
        List<Department> departments = memberManagementService.getDepartmentByUser(id);
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("getWorkingTime/{id}")
    public ResponseEntity<?> getWorkingTime(@PathVariable Long id) {
        List<WorkingTime> workingTimes = memberManagementService.getWorkingTimebyUser(id);
        return ResponseEntity.ok().body(workingTimes);
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User newUser = new User();
        newUser.setUserName(userDTO.getUserName());
        newUser.setUserFullName(userDTO.getUserFullName());
        newUser.setUserPasswords(passwordEncoder.encode(userDTO.getPassword()));
        Set<PositionDTO> positionDTOS = userDTO.getPositions();
        Set<Position> positions = new HashSet<>();
        for (PositionDTO positionDTO : positionDTOS) {
            Position position;
            Optional<Position> optionalPosition = positionRepository.findById(positionDTO.getId());
            if (optionalPosition.isPresent()) {
                position = optionalPosition.get();
            } else {
                position = new Position();
                position.setPositionName(positionDTO.getPositionName());
            }
            positions.add(position);
        }
        newUser.setPositions(positions);
        //Set department of departmentDTOs for department
        Set<DepartmentDTO> departmentDTOS = userDTO.getDepartments();
        Set<Department> departments = new HashSet<>();
        for(DepartmentDTO departmentDTO : departmentDTOS){
            Department department;
            Optional<Department> optionalDepartment = departmentRepository.findById(departmentDTO.getId());
            if (optionalDepartment.isPresent()) {
                department = optionalDepartment.get();
            } else {
                department = new Department();
                department.setDepartmentName(departmentDTO.getDepartmentName());
            }
            departments.add(department);
        }
        newUser.setDepartments(departments);
        memberManagementService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User existingUser = userOptional.get();
        existingUser.setId(userDTO.getId());
        existingUser.setUserName(userDTO.getUserName());
        existingUser.setUserFullName(userDTO.getUserFullName());
        Set<PositionDTO> positionDTOS = userDTO.getPositions();
        Set<Position> positions = new HashSet<>();
        for (PositionDTO positionDTO : positionDTOS) {
            Position position = new Position();
            position.setId(positionDTO.getId());
            position.setPositionName(positionDTO.getPositionName());
            positions.add(position);
        }
        existingUser.setPositions(positions);
        memberManagementService.save(existingUser);
        return new ResponseEntity<>(existingUser, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        memberManagementService.delete(userOptional.get());
        return new ResponseEntity<>(userOptional.get(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<UserExcelDTO> listUsers = memberManagementService.getUsersExcel();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);

    }

    @GetMapping("/exportCSV")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<UserExcelDTO> listUsers = memberManagementService.getUsersExcel();

        UserCSVExporter csvExporter = new UserCSVExporter(listUsers);

        csvExporter.generateCSV(response);

    }
}