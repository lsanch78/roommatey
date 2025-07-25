package com.roommatey;

import com.roommatey.model.*;
import com.roommatey.repository.HouseholdRepository;
import com.roommatey.repository.UserRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.format.DateTimeFormatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Controller
public class HomeController {

    private final HouseholdRepository householdRepo;
    private final UserRepository userRepo;

    public HomeController(HouseholdRepository householdRepo, UserRepository userRepo) {
        this.householdRepo = householdRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String home(Model model) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);

        if (household == null) {
            return "welcome";
        }

        model.addAttribute("household", household);

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d");
        String formatted = today.format(formatter) + getDaySuffix(today.getDayOfMonth());
        model.addAttribute("todayFormatted", formatted);

        Map<User, List<Bill>> userBills = new HashMap<>();
        for (User user : userRepo.findAll()) {
            List<Bill> bills = user.getShares().stream()
                    .map(BillShare::getBill)
                    .filter(b -> b.getDueDate().isAfter(today.minusDays(1)))
                    .sorted(Comparator.comparing(Bill::getDueDate))
                    .toList();

            userBills.put(user, bills);
        }

        Map<User, List<Chore>> userChores = new HashMap<>();
        for (User user : userRepo.findAll()) {
            List<Chore> chores = user.getChores() == null ? List.of() : user.getChores();
            userChores.put(user, chores);
        }

        model.addAttribute("userBills", userBills);
        model.addAttribute("userChores", userChores);

        return "index";
    }
    private String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) return "th";
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

}
