package com.example.taskmanager.domain;

import java.util.Date;

public class Task {

    private Long id;
    private String name;
    private String description;
    private Date dueDate;
    private int priority;  // Esimerkiksi 1 = korkea, 2 = keskitaso, 3 = matala
    private String status; // Esimerkiksi "Kesken", "Valmis", "Ei aloitettu"
    private Long userId;   // Viite käyttäjään, joka omistaa tehtävän
    private Long categoryId; // Viite kategorian id:hen, johon tehtävä kuuluu
    
    
    public Task() {
    }

  
    public Task(Long id, String name, String description, Date dueDate, int priority, String status, Long userId, Long categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.userId = userId;
        this.categoryId = categoryId;
    }

    // Getterit ja setterit
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    // ToString-metodi helpottamaan tehtävän tulostamista
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                '}';
    }
}
