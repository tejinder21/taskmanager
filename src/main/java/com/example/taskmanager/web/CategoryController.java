package com.example.taskmanager.web;

import com.example.taskmanager.domain.Category;
import com.example.taskmanager.domain.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lomake uuden kategorian lisäämistä varten
    @GetMapping("/addcategory")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "addcategory";
    }

    // Kategoriat-listaus
    @GetMapping("/categorylist")
    public String showCategoryList(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "categorylist";
    }

    // Uuden kategorian tallentaminen
    @PostMapping("/savecategory")
    public String saveCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/tasklist"; // Voit vaihtaa tämän mihin tahansa näkymään, esim. "/categorylist" jos teet
                                     // myöhemmin listauksen
    }

    // Kategorian poistaminen
    @GetMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/tasklist"; // Sama juttu, voit muuttaa tämän tarpeen mukaan
    }
}
