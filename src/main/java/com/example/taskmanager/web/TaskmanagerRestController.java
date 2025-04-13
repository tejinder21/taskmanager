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
@RequestMapping("/api/tasks")  // Määritellään API-päätepiste
public class TaskmanagerRestController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // 1. Palauta kaikki tehtävät
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Palauttaa kaikki tehtävät JSON-muodossa
    }

    // 2. Palauta yksi tehtävä ID:n perusteella
    @GetMapping("/{id}")
    public Optional<Task> getTaskById(@PathVariable("id") Long id) {
        return taskRepository.findById(id); // Palauttaa tehtävän ID:n perusteella
    }

    // 3. Tallenna uusi tehtävä
    @PostMapping                                                                            
    public Task saveTask(@RequestBody Task task) {
        return taskRepository.save(task); // Tallentaa uuden tehtävän
    }

    // 4. Hae kaikki kategoriat
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // Palauttaa kaikki kategoriat
    }
}
