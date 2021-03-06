package com.example.demo.controller;

import com.example.demo.model.Priority;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class CalendarController {


    //tutaj dodajemy wszystkie metody zwiazane z kalendarzem, czasem itp. metody dotyczace uzytkownika aplikacji sa w UserController

    private TaskService taskService;
    private UserService userService;

    @Autowired
    public CalendarController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    public User findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return userService.findUserByUsername(currentUserName);
        }
        else
            return null;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public List<Task> showTasks(){
        return taskService.findAllTasksByUser(findUser());
    }

}
