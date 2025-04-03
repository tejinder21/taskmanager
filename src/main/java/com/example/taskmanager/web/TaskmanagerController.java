package com.example.taskmanager.web;

import com.example.taskmanager.domain.CategoryRepository;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskmanagerController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/tasklist")
    public String showTaskList(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasklist";
    }

    // Uuden tehtävän lisääminen
    @GetMapping("/addtask")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addtask";
    }

    @PostMapping("/addtask")
    public String addTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasklist";
    }

    // Tehtävän muokkaaminen
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).orElse(null));
        model.addAttribute("categories", categoryRepository.findAll());
        return "edittask";
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task) {
        task.setId(id); // Varmistetaan, että ID säilyy
        taskRepository.save(task);
        return "redirect:/tasklist";
    }

    // Tehtävän poistaminen
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasklist";
    }
}
