package com.example.taskmanager.web;

import com.example.taskmanager.domain.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskmanagerController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasklist")
    public String showTaskList(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasklist";
    }
}
