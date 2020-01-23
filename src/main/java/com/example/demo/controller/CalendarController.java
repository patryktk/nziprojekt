package com.example.demo.controller;

import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class CalendarController {

    //tutaj dodajemy wszystkie metody zwiazane z kalendarzem, czasem itp. metody dotyczace uzytkownika aplikacji sa w UserController

    private TaskService taskService;
    private UserService userService;

    Date date = new Date();

    @Autowired
    public CalendarController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }



}
