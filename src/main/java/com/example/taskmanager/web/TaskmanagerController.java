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
        return "tasklist"; // Palauttaa "tasklist.html"
    }

    // Lomake uuden tehtävän lisäämistä varten
    @GetMapping("/addtask")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task()); // Luo tyhjän Task-olion
        model.addAttribute("categories", categoryRepository.findAll()); // Hakee kaikki kategoriat
        return "addtask"; // Palauttaa "addtask.html"
    }

    // Uuden tehtävän tallentaminen
    @PostMapping("/addtask")
    public String addTask(@ModelAttribute Task task) {
        taskRepository.save(task); // Tallentaa tehtävän tietokantaan
        return "redirect:/tasklist"; // Uudelleenohjaa tasklist-sivulle
    }

    // Kirjautumissivu
    @GetMapping("/login")
    public String login() {
        return "login"; // Palauttaa "login.html"
    }

    // Lomake tehtävän muokkaamista varten
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).orElse(null)); // Etsii tehtävän ID:n perusteella
        model.addAttribute("categories", categoryRepository.findAll()); // Listaa kategoriat
        return "edittask"; // Palauttaa "edittask.html"
    }

    // Tehtävän päivittäminen
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task) {
        task.setId(id); // Varmistaa, että ID säilyy
        taskRepository.save(task); // Tallentaa tehtävän tietokantaan
        return "redirect:/tasklist"; // Uudelleenohjaa tasklist-sivulle
    }

    // Tehtävän poistaminen
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id); // Poistaa tehtävän ID:n perusteella
        return "redirect:/tasklist"; // Uudelleenohjaa tasklist-sivulle
    }

    // Tehtävän tilan vaihtaminen
    @GetMapping("/toggleStatus/{id}")
    public String toggleTaskStatus(@PathVariable Long id) {
        // Etsi tehtävä ID:n perusteella
        Task task = taskRepository.findById(id).orElse(null);

        // Jos tehtävä löytyi, vaihdetaan sen tila
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
            // Tallenna tehtävä tietokantaan
            taskRepository.save(task);
        }

        // Palauta takaisin tehtävälistalle
        return "redirect:/tasklist";
    }
}
