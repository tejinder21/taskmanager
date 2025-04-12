package com.example.taskmanager.web;

import com.example.taskmanager.domain.CategoryRepository;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TaskmanagerController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/tasklist")
    public String showTaskList(@RequestParam(required = false) String search, Model model) {
        List<Task> tasks;

        if (search != null && !search.isEmpty()) {
            tasks = taskRepository.findByNameContainingIgnoreCase(search);
        } else {
            tasks = taskRepository.findAll();
        }

        tasks.sort((task1, task2) -> Integer.compare(task1.getPriority(), task2.getPriority()));

        model.addAttribute("tasks", tasks);
        model.addAttribute("search", search);

        // ➕ Statistiikka ja edistymispalkki
        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(t -> "Completed".equals(t.getStatus())).count();
        long inProgressTasks = totalTasks - completedTasks;
        double progressPercent = totalTasks > 0 ? ((double) completedTasks / totalTasks) * 100 : 0;

        model.addAttribute("totalTasks", totalTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("progressPercent", progressPercent);

        return "tasklist";
    }

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

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).orElse(null));
        model.addAttribute("categories", categoryRepository.findAll());
        return "edittask";
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task) {
        task.setId(id);
        taskRepository.save(task);
        return "redirect:/tasklist";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasklist";
    }

    @GetMapping("/toggleStatus/{id}")
    public String toggleTaskStatus(@PathVariable Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            switch (task.getStatus()) {
                case "Not Started":
                    task.setStatus("In Progress");
                    break;
                case "In Progress":
                    task.setStatus("Completed");
                    break;
                case "Completed":
                    task.setStatus("Not Started");
                    break;
                default:
                    task.setStatus("Not Started");
            }
            taskRepository.save(task);
        }
        return "redirect:/tasklist";
    }

    @PostMapping("/updatePriority/{id}")
    @ResponseBody
    public ResponseEntity<Void> updatePriority(@PathVariable Long id, @RequestBody Integer newPriority) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setPriority(newPriority);
            taskRepository.save(task);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
