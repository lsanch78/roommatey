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
    public String register(@ModelAttribute User user) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        if (household == null) {
            return "redirect:/household/create"; // fallback
        }
        user.setHousehold(household);
        userRepo.save(user);
        return "redirect:/users/registered";
    }



    @GetMapping("/register")
    public String showForm(Model model) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        model.addAttribute("user", new User());
        model.addAttribute("household", household);
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

    @GetMapping("/manage")
    public String manageUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "user-manage";
    }
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepo.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id); // ensure update not insert
        userRepo.save(user);
        return "redirect:/users/manage";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/users/manage";
    }




}
