package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByUserAndStartIsAfterAndEndIsBefore(User user, Date start, Date end);
}
