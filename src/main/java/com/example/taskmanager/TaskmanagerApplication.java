package com.example.taskmanager;

import com.example.taskmanager.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class TaskmanagerApplication implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Käytetään injektoitua PasswordEncoderia

    public static void main(String[] args) {
        SpringApplication.run(TaskmanagerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Kategoriat
        Category workCategory = new Category("Work");
        Category homeCategory = new Category("Home");
        Category shoppingCategory = new Category("Shopping");

        categoryRepository.save(workCategory);
        categoryRepository.save(homeCategory);
        categoryRepository.save(shoppingCategory);

        // Tehtävät
        taskRepository.save(new Task("Complete project report", "Send to client",
                LocalDate.of(2025, 3, 31), 1, "Not Started", workCategory));
        taskRepository.save(new Task("Buy groceries", "Weekly groceries",
                LocalDate.of(2025, 3, 29), 2, "In Progress", homeCategory));
        taskRepository.save(new Task("Clean the house", "Do cleaning",
                LocalDate.of(2025, 3, 28), 3, "Completed", homeCategory));

        // Käyttäjät (salasana = käyttäjätunnus)
        userRepository.save(new AppUser("user", passwordEncoder.encode("user"), List.of("ROLE_USER")));
        userRepository.save(new AppUser("admin", passwordEncoder.encode("admin"), List.of("ROLE_ADMIN")));
    }
}
