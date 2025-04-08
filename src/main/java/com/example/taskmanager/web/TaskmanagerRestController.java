package com.example.taskmanager.web;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskRepository;
import com.example.taskmanager.domain.Category;
import com.example.taskmanager.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin  // Salli eri alkuperäiset pyynnöt (esim. frontend localhostilta)
@RestController
@RequestMapping("/api/tasks")
public class TaskmanagerRestController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Palauta kaikki tehtävät
    @GetMapping
    public @ResponseBody List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 2. Palauta yksi tehtävä ID:n perusteella
    @GetMapping("/{id}")
    public @ResponseBody Optional<Task> getTaskById(@PathVariable("id") Long id) {
        return taskRepository.findById(id);
    }

    // 3. Tallenna uusi tehtävä
    @PostMapping
    public @ResponseBody Task saveTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // 4. Hae kaikki kategoriat
    @GetMapping("/categories")
    public @ResponseBody List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
