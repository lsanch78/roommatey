package com.roommatey.controller;

import com.roommatey.model.Household;
import com.roommatey.model.User;
import com.roommatey.repository.HouseholdRepository;
import com.roommatey.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;
    private final HouseholdRepository householdRepo;

    public UserController(UserRepository userRepo, HouseholdRepository householdRepo) {
        this.userRepo = userRepo;
        this.householdRepo = householdRepo;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, @RequestParam("household.id") Long householdId) {
        Household household = householdRepo.findById(householdId).orElse(null);
        user.setHousehold(household);
        userRepo.save(user);
        return "redirect:/users/registered";
    }


    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("households", householdRepo.findAll());
        return "register-user";
    }

    @GetMapping("/registered")
    public String done() {
        return "user-registered";
    }

    @GetMapping("/all")
    public String allUsers(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }
}
