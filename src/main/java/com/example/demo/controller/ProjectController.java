package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.repository.Repository;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер CRUD для сущности Project.
 * Просмотр списка доступен USER/ADMIN (настраивается в SecurityConfig),
 * модификация ограничена ADMIN (дополнительно дублируется @PreAuthorize).
 */
@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final Repository projectRepository;

    public ProjectController(Repository projectRepository) {
        this.projectRepository = projectRepository;
    }

    /** Список проектов + форма добавления (форма видна только ADMIN в шаблоне) */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("projects", projectRepository.findAll());
        // пустой объект для формы добавления
        model.addAttribute("project", new Project());
        return "projects";
    }

    /** Создание проекта — только ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("project") Project project,
                      BindingResult bindingResult,
                      Model model) {

        if (bindingResult.hasErrors()) {
            // Перерисовываем список с ошибками в форме
            model.addAttribute("projects", projectRepository.findAll());
            return "projects";
        }
        projectRepository.save(project);
        return "redirect:/projects";
    }

    /** Страница редактирования — только ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Проект не найден: " + id));
        model.addAttribute("project", project);
        return "edit_project";
    }

    /** Сохранение изменений — только ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String saveEdited(@PathVariable Long id,
                             @Valid @ModelAttribute("project") Project project,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit_project";
        }
        // гарантируем корректный id
        project.setId(id);
        projectRepository.save(project);
        return "redirect:/projects";
    }

    /** Удаление — только ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            // мягко игнорируем: просто возвращаемся к списку
            return "redirect:/projects";
        }
        projectRepository.deleteById(id);
        return "redirect:/projects";
    }
}
