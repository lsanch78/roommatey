package com.roommatey.controller;

import com.roommatey.model.Household;
import com.roommatey.repository.HouseholdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final HouseholdRepository householdRepo;

    public HomeController(HouseholdRepository householdRepo) {
        this.householdRepo = householdRepo;
    }

    @GetMapping("/")
    public String home(Model model) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        model.addAttribute("household", household);
        return "index"; // maps to index.html
    }
}
