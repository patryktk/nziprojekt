package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepo;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    static final private String DATE_FORMAT = "dd/MM/yyyy";
    private DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern(DATE_FORMAT);
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    private TaskRepo taskRepo;
    private UserService userService;

    @Autowired
    public TaskServiceImpl(TaskRepo taskRepo, UserService userService) {
        this.taskRepo = taskRepo;
        this.userService = userService;
    }

    //zwraca listę wszystkich dni wybranego miesiąca
    @Override
    public List<String> createMonthLabels(LocalDate month){
        List<String> daysInMonthLabels = new ArrayList<String>();
        LocalDate firstDay = month.withDayOfMonth(1);
        LocalDate nextMonthFirstDay = firstDay.plusMonths(1);
        while (firstDay.isBefore(nextMonthFirstDay)) {
            daysInMonthLabels.add(formatter.print(firstDay));
            firstDay = firstDay.plusDays(1);
        }
        return daysInMonthLabels;
    }

    @Override
    public List<Task> findsTasks(Date start, Date end) {
        User accInfo = userService.findUserByUsername(authentication.getName());
        return taskRepo.findAllByUserAndStartIsAfterAndEndIsBefore(accInfo, start, end);
    }

    @Override
    public boolean saveTask(Task task) {
        taskRepo.save(task);
        return taskRepo.existsById(task.getId());
    }

    @Override
    public boolean deleteTask(Long id) {
        taskRepo.deleteById(id);
        return (!taskRepo.existsById(id));
    }
}
