package com.example.demo.controller;

import com.example.demo.repository.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final Repository projectRepository;

    public MainController(Repository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        return "index"; // index.html
    }
}
