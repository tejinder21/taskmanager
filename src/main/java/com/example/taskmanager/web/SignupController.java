package com.example.taskmanager.web;

import com.example.taskmanager.domain.AppUser;
import com.example.taskmanager.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Injektoitu PasswordEncoder

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";  // Palauttaa signup-sivun
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String password2, // Salasanan vahvistus
                                Model model) {

        // Tarkista, onko käyttäjä jo olemassa
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already exists.");
            return "signup";  // Jos käyttäjä on jo olemassa, näytetään virhe
        }

        // Varmista, että salasanat täsmäävät
        if (!password.equals(password2)) {
            model.addAttribute("error", "Passwords do not match.");
            return "signup";  // Jos salasanat eivät täsmää, näytetään virhe
        }

        // Salasanan salaaminen
        String hashedPassword = passwordEncoder.encode(password);
        AppUser newUser = new AppUser(username, hashedPassword, List.of("ROLE_USER"));
        userRepository.save(newUser);  // Tallennetaan uusi käyttäjä tietokantaan

        return "redirect:/login";  // Uudelleenohjaa kirjautumissivulle
    }
}
