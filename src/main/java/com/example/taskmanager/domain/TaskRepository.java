package com.example.taskmanager.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Haetaan tehtäviä, joiden nimi sisältää annetun hakusanan (case-insensitive)
    List<Task> findByNameContainingIgnoreCase(String name);
}
