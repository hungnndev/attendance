package com.example.demo.controller.web;

import com.example.demo.dto.request.AuthenticationRequest;
import com.example.demo.dto.response.AuthenticationResponse;
import com.example.demo.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JwtController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/token") // when trying this url,select auth type: No Auth
    public String generateToken(Model m, HttpSession session,
                                @ModelAttribute AuthenticationRequest authenticationRequest, HttpServletResponse res) throws Exception
    {
        AuthenticationResponse result;
        try {
            result = authenticationService.authenticate(authenticationRequest);
        } catch (UsernameNotFoundException e) {

            session.setAttribute("msg","Bad Credentials");
            return "redirect:/loginui";

        } catch(BadCredentialsException e)
        {
            session.setAttribute("msg","Bad Credentials");
            return "redirect:/loginui";
        }

        try {
            final String token = result.getToken();


            Cookie cookie = new Cookie("token",token);
            cookie.setMaxAge(Integer.MAX_VALUE);
            res.addCookie(cookie);

            return "redirect:/userui";
        }catch(Exception e)
        {
            session.setAttribute("msg","Credentials were right But something went wrong!!");
            return "redirect:/loginui";
        }
    }
}
