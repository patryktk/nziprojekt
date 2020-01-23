package com.example.demo.service;

import com.example.demo.model.Task;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

public interface TaskService {

    List<String> createMonthLabels(LocalDate month);

    List<Task> findsTasks(Date start, Date end);

    boolean saveTask(Task task);

    boolean deleteTask(Long id);
}
