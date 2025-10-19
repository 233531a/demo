package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.repository.Repository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final Repository projectRepository;

    public ProjectController(Repository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("project", new Project());
        return "projects";
    }

    @PostMapping("/add")
    public String addProject(@Valid @ModelAttribute Project project, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("projects", projectRepository.findAll());
            return "projects";
        }
        projectRepository.save(project);
        return "redirect:/projects";
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }

    @GetMapping("/edit/{id}")
    public String editProjectForm(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectRepository.findById(id).orElseThrow());
        return "edit_project";
    }

    @PostMapping("/edit/{id}")
    public String saveEditedProject(@PathVariable Long id, @Valid @ModelAttribute Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_project";
        }
        project.setId(id);
        projectRepository.save(project);
        return "redirect:/projects";
    }
}
