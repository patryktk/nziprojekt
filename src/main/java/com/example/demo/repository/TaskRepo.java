package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    Task findByTitle(String title);
    List<Task> findAllByUser(User user);
}
