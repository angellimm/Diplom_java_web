package com.diary.demo.Controllers;

import com.diary.demo.Models.MyUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class WebController {

    @GetMapping("/home")
    public String handleWelcome() {
        return "home";
    }

//    @GetMapping("/admin/home")
//    public String handleAdminHome() {
//        return "home_admin";
//    }

    @GetMapping("/user/home")
    public String handleUserHome() {
        return "home_user";
    }

    @GetMapping("/login")
    public String handleLogin() {
        return "custom-login";
    }

//    @GetMapping("/register")
//    public String handleRegister() {
//        return "custom-register";
//    }
//@GetMapping("/register")
//public String showRegistrationForm(Model model) {
//    model.addAttribute("user", new MyUser());
//    return "register";
//}


}
