package com.roommatey.controller;

import com.roommatey.model.Chore;
import com.roommatey.model.ChoreType;
import com.roommatey.model.Frequency;
import com.roommatey.model.Household;
import com.roommatey.repository.ChoreRepository;
import com.roommatey.repository.HouseholdRepository;
import com.roommatey.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.DayOfWeek;

@Controller
@RequestMapping("/chores")
public class ChoreController {

    private final ChoreRepository choreRepo;
    private final HouseholdRepository householdRepo;
    private final UserRepository userRepo;

    public ChoreController(ChoreRepository choreRepo, HouseholdRepository householdRepo, UserRepository userRepo) {
        this.choreRepo = choreRepo;
        this.householdRepo = householdRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/all")
    public String viewChores(Model model) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        model.addAttribute("chores", choreRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("household", household);
        return "chore-manage";
    }

    @GetMapping("/new")
    public String newChore(Model model, RedirectAttributes redirectAttrs) {
        if (userRepo.count() == 0) {
            redirectAttrs.addFlashAttribute("error", "You must add at least one roommate before creating a chore.");
            return "redirect:/users/register";
        }
        model.addAttribute("chore", new Chore());
        model.addAttribute("frequencies", Frequency.values());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("types", ChoreType.values());
        return "chore-create";
    }

    @PostMapping("/create")
    public String createChore(@ModelAttribute Chore chore,
                              @RequestParam(required = false) Integer recurringDayOfMonth,
                              @RequestParam(required = false) String recurringDayOfWeek,
                              @RequestParam("userId") Long userId) {
        chore.setHousehold(householdRepo.findAll().stream().findFirst().orElse(null));
        chore.setAssignedTo(userRepo.findById(userId).orElse(null));
        chore.setRecurringDayOfMonth(recurringDayOfMonth);
        if (recurringDayOfWeek != null && !recurringDayOfWeek.isEmpty()) {
            chore.setRecurringDayOfWeek(DayOfWeek.valueOf(recurringDayOfWeek));
        }
        choreRepo.save(chore);
        return "redirect:/chores/all";
    }


    @GetMapping("/edit/{id}")
    public String editChore(@PathVariable Long id, Model model) {
        Chore chore = choreRepo.findById(id).orElseThrow();
        model.addAttribute("chore", chore);
        model.addAttribute("frequencies", Frequency.values());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("types", ChoreType.values());
        return "chore-edit.html";
    }


    @PostMapping
    public String saveChore(@ModelAttribute Chore chore,
                            @RequestParam("userId") Long userId) {
        chore.setHousehold(householdRepo.findAll().stream().findFirst().orElse(null));
        chore.setAssignedTo(userRepo.findById(userId).orElse(null));
        choreRepo.save(chore);
        return "redirect:/chores/manage";
    }

    @PostMapping("/update/{id}")
    public String updateChore(@PathVariable Long id,
                              @ModelAttribute Chore updated,
                              @RequestParam("userId") Long userId,
                              @RequestParam(required = false) Integer recurringDayOfMonth,
                              @RequestParam(required = false) String recurringDayOfWeek) {
        Chore chore = choreRepo.findById(id).orElseThrow();
        chore.setName(updated.getName());
        chore.setInstructions(updated.getInstructions());
        chore.setFrequency(updated.getFrequency());
        chore.setAssignedTo(userRepo.findById(userId).orElse(null));
        chore.setRecurringDayOfMonth(recurringDayOfMonth);
        if (recurringDayOfWeek != null && !recurringDayOfWeek.isEmpty()) {
            chore.setRecurringDayOfWeek(DayOfWeek.valueOf(recurringDayOfWeek));
        } else {
            chore.setRecurringDayOfWeek(null);
        }
        choreRepo.save(chore);
        return "redirect:/chores/manage";
    }

    @GetMapping("/delete/{id}")
    public String deleteChore(@PathVariable Long id) {
        choreRepo.deleteById(id);
        return "redirect:/chores/all";
    }
}
