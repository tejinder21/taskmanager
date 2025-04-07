package com.example.taskmanager.web;

import com.example.taskmanager.domain.CategoryRepository;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Näyttää tehtävälistan ja järjestää tehtävät prioriteetin mukaan
    @GetMapping("/tasklist")
    public String showTaskList(Model model) {
        List<Task> tasks = taskRepository.findAll();
        
        // Järjestetään tehtävät prioriteetin mukaan (pienin numero ensin)
        tasks.sort((task1, task2) -> Integer.compare(task1.getPriority(), task2.getPriority()));

        model.addAttribute("tasks", tasks);
        return "tasklist";
    }

    // Lomake uuden tehtävän lisäämistä varten
    @GetMapping("/addtask")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addtask";
    }

    // Uuden tehtävän tallentaminen
    @PostMapping("/addtask")
    public String addTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasklist";
    }

    // Lomake tehtävän muokkaamista varten
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).orElse(null));
        model.addAttribute("categories", categoryRepository.findAll());
        return "edittask";
    }

    // Tehtävän päivittäminen
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
