package com.example.taskmanager;

import com.example.taskmanager.domain.Category;
import com.example.taskmanager.domain.CategoryRepository;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.domain.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
@SpringBootApplication
public class TaskmanagerApplication implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(TaskmanagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // kategoriat
        Category workCategory = new Category("Work");
        Category homeCategory = new Category("Home");
        Category shoppingCategory = new Category("Shopping");
        
        categoryRepository.save(workCategory);
        categoryRepository.save(homeCategory);
        categoryRepository.save(shoppingCategory);

        // Create example tasks and assign categories
        Task task1 = new Task("Complete project report", "Finalize the report and send it to the client.",
                LocalDate.of(2025, 3, 31), 1, "Not Started", workCategory);
        
        Task task2 = new Task("Buy groceries", "Purchase groceries for the week.",
                LocalDate.of(2025, 3, 29), 2, "In Progress", homeCategory);

        Task task3 = new Task("Clean the house", "Do the weekly house cleaning.",
                LocalDate.of(2025, 3, 28), 3, "Completed", homeCategory);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        System.out.println("Example tasks and categories inserted into the database.");
    }
}
