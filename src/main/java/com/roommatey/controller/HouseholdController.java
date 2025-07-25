package com.roommatey.controller;

import com.roommatey.model.Household;
import com.roommatey.repository.HouseholdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/household")
public class HouseholdController {

    private final HouseholdRepository householdRepo;

    public HouseholdController(HouseholdRepository householdRepo) {
        this.householdRepo = householdRepo;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!householdRepo.findAll().isEmpty()) {
            return "redirect:/household/exists";
        }
        model.addAttribute("household", new Household());
        return "household-create";
    }

    @PostMapping("/create")
    public String createHousehold(@ModelAttribute Household household) {
        if (!householdRepo.findAll().isEmpty()) {
            return "redirect:/household/exists";
        }
        householdRepo.save(household);
        return "redirect:/household/created";
    }

    @GetMapping("/exists")
    public String alreadyExists() {
        return "household-exists";
    }

    @GetMapping("/manage")
    public String manageHousehold(Model model) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        if (household == null) {
            return "redirect:/household/create";
        }
        model.addAttribute("household", household);
        return "household-manage";
    }


    @PostMapping("/manage")
    public String updateHousehold(@ModelAttribute Household household) {
        householdRepo.save(household); // Updates the name of the existing household
        return "redirect:/";
    }


    @GetMapping("/created")
    public String createdMessage() {
        return "household-created";
    }
}
