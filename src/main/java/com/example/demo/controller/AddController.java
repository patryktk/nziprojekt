package com.example.demo.controller;


import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepo;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AddController {


    private TaskService taskService;
    private TaskRepo taskRepo;
    private UserService userService;

    @Autowired
    public AddController(TaskService taskService, UserService userService, TaskRepo taskRepo) {
        this.taskService = taskService;
        this.userService = userService;
        this.taskRepo = taskRepo;
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

    @GetMapping("/addtask")
    public String add(Task task, Model model){
        model.addAttribute(task);
        return "adds";
    }

    @PostMapping("/add")
    public String addTask(Task task, Model model){
        task.setUser(findUser());
        taskService.saveTask(task);
        return "mainpage";
    }

    @GetMapping("/deletetask")
    public String delete(){
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteTask(@RequestParam Long id){
        taskService.deleteTask(id);
        return "mainpage";
    }
}
