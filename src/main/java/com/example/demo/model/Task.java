package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "start", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;
    @Column(name = "end", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;
    @Column(name = "priority")
    private Priority priority;

    public Task(User user, String content, Date start, Date end, Priority priority) {
        this.user = user;
        this.content = content;
        this.start = start;
        this.end = end;
        this.priority = priority;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", user=" + user +
                ", content='" + content + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", priority=" + priority +
                '}';
    }
}
