package com.example.demo.controller;
//import com.example.demo.model.MemberManagement;
//import com.example.service.IMemberManagementService;
import com.example.demo.dto.UserExcelDTO;
import com.example.demo.excel.UserExcelExporter;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkingTime;
//import com.example.demo.service.ExcelService;
import com.example.demo.service.MemberManagementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/user/member")
public class MemberManagementController {
    @Autowired
    private MemberManagementService memberManagementService;

    @GetMapping

    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(memberManagementService.getAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getAllUserById(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        return userOptional.map(user -> new ResponseEntity<User>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getAllUserById() {
//        return ResponseEntity.ok().body(memberManagementService.getAllUserById());
//    }

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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        memberManagementService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        memberManagementService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        memberManagementService.remove(id);
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
//    private  ExcelService excelService;
//
//    public void ExcelController(ExcelService excelService) {
//        this.excelService = excelService;
//    }
//
//    @PostMapping("/convertToCsv")
//    public ResponseEntity<String> convertExcelToCsv(@RequestParam("file") MultipartFile file) {
//        try {
//            String csvFilePath = excelService.convertExcelToCsv(file);
//            return ResponseEntity.ok("File CSV đã được lưu tại: " + csvFilePath);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Chuyển đổi thất bại");
//        }
}

