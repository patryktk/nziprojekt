package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.EmailServiceImpl;
import com.example.demo.service.UserDetailsServiceImpl;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    //controller wszystkiego co zwiazane z uzytkownikiem

    private UserDetailsServiceImpl userDetailsService;
    private UserService userService;
    private UserRepo userRepo;
    private EmailServiceImpl emailServiceImpl;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsService, EmailServiceImpl emailServiceImpl, PasswordEncoder passwordEncoder, UserService userService, UserRepo userRepo) {
        this.userDetailsService = userDetailsService;
        this.emailServiceImpl = emailServiceImpl;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/register")
    public String add(User user, Model model) {
        return "signup";
    }

    //poniżej przykład jak możecie sb dodawać metody z serwisów w controllerze
    //one zwracają true i false w większośći więc nie trzeba się jebać z jakimś złożonym kodem
    //w przypadków większości funkcjonalności wystarczy wywyołać odpowiednią metodę z pobranymi od użytkownika parametrami
    //ona sobie zwraca true jeżeli operacja przebiegła dobrze (np user został dodany) lub false
    //i wtedy tylko sobie odpowiedni return itp podstawiacie


    @PostMapping("/signup")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        boolean check = userService.saveUser(user);
        if (result.hasErrors()) {
            return "login-error";
        }
        if (!check) {
            return "login-error";
        } else {
            return "confirmInfo";
        }
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token, User user, Model model) {
        boolean check = userService.checkConfirmation(token);
        if (!check) {
            return "login-error";
        } else {
            userRepo.findByConfirmationToken(token).setEnabled(true);
            return "confirmemail";
        }
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/choise")
    public String choise(){
        return "choise";
    }

    @GetMapping("/regulamin")
    public String regu(){
        return "regulationsPage";
    }

    @GetMapping("/servicepasswrd")
    public String servicepassword(){
        return "servicePasswrd";
    }

    @GetMapping("/resetinfo")
    public String resetInfo() {
        return "resetInfo";
    }


    @GetMapping("/confirminfo")
    public String confirm(){
        return "confirmInfo";
    }


    @RequestMapping("/mainpage")
    public String mainpage(){
        return "mainpage";
    }

}
