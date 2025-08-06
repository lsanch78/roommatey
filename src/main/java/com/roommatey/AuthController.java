package com.roommatey;

import com.roommatey.model.User;
import com.roommatey.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {


    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "logout";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user) {
        // Check if user's phone exists
        if (userRepo.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            return "redirect:/register?failure";
        }


        // encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        // redirect to login page after successful registration
        return "redirect:/register?success";
    }
}
