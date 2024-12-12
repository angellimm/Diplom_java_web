package com.diary.demo.Controllers;

import com.diary.demo.Models.MyUser;
import com.diary.demo.Repository.MyUserRepository;
import com.diary.demo.Service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
//public class RegistrationController {
//
//    @Autowired
//    private MyUserRepository myUserRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public MyUser createUser(@RequestBody MyUser user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return myUserRepository.save(user);
//    }
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private MyUserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new MyUser());
        return "custom-register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid MyUser user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/login";
        }}


