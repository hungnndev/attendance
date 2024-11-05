package com.example.demo.web;

import com.example.demo.model.User;
import com.example.demo.service.MemberManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private MemberManagementService memberManagementService;

    @GetMapping(value = "/user")
    public String getUser(Model model) {
        List<User> users = (List<User>) memberManagementService.findAll();

        model.addAttribute("users", users);

        return "user/index";
    }
}
