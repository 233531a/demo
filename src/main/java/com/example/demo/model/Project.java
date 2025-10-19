package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название проекта не может быть пустым")
    @Size(max = 100, message = "Название проекта слишком длинное")
    private String projectName;

    @NotBlank(message = "Имя менеджера не может быть пустым")
    @Size(max = 50, message = "Имя менеджера слишком длинное")
    private String manager;

    @FutureOrPresent(message = "Дедлайн должен быть сегодняшним или в будущем")
    private LocalDate deadline;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
}
