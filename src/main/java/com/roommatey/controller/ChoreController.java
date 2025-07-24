package com.roommatey.controller;

import com.roommatey.model.Chore;
import com.roommatey.model.Frequency;
import com.roommatey.model.Household;
import com.roommatey.repository.ChoreRepository;
import com.roommatey.repository.HouseholdRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chores")
public class ChoreController {

    private final ChoreRepository choreRepo;
    private final HouseholdRepository householdRepo;

    public ChoreController(ChoreRepository choreRepo, HouseholdRepository householdRepo) {
        this.choreRepo = choreRepo;
        this.householdRepo = householdRepo;
    }

    @GetMapping("/all")
    public String viewChores(Model model) {
        model.addAttribute("chores", choreRepo.findAll());
        return "chore-list";
    }

    @GetMapping("/new")
    public String newChoreForm(Model model) {
        model.addAttribute("chore", new Chore());
        model.addAttribute("frequencies", Frequency.values());
        return "chore-create";
    }

    @PostMapping
    public String createChore(@ModelAttribute Chore chore) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        chore.setHousehold(household);
        choreRepo.save(chore);
        return "redirect:/chores/all";
    }

    @GetMapping("/edit/{id}")
    public String editChore(@PathVariable Long id, Model model) {
        Chore chore = choreRepo.findById(id).orElseThrow();
        model.addAttribute("chore", chore);
        model.addAttribute("frequencies", Frequency.values());
        return "chore-edit";
    }

    @PostMapping("/update/{id}")
    public String updateChore(@PathVariable Long id, @ModelAttribute Chore updatedChore) {
        Chore chore = choreRepo.findById(id).orElseThrow();
        chore.setName(updatedChore.getName());
        chore.setInstructions(updatedChore.getInstructions());
        chore.setFrequency(updatedChore.getFrequency());
        choreRepo.save(chore);
        return "redirect:/chores/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteChore(@PathVariable Long id) {
        choreRepo.deleteById(id);
        return "redirect:/chores/all";
    }
}
