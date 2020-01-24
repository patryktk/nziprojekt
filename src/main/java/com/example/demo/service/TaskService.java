package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.joda.time.LocalDate;

import java.util.List;

public interface TaskService {

    List<Task> findAllTasksByUser(User user);

    Task saveTask(Task task);

    boolean deleteTask(Long id);
}
