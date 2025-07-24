package com.roommatey.controller;

import com.roommatey.model.Bill;
import com.roommatey.model.BillShare;
import com.roommatey.model.Household;
import com.roommatey.model.User;
import com.roommatey.repository.HouseholdRepository;
import com.roommatey.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        model.addAttribute("household", household);

        LocalDate today = LocalDate.now();
        DayOfWeek day = today.getDayOfWeek();

        model.addAttribute("day", day);
        model.addAttribute("date", today);

        Map<User, List<Bill>> userBills = new HashMap<>();
        for (User user : userRepo.findAll()) {
            List<Bill> bills = user.getShares().stream()
                    .map(BillShare::getBill)
                    .filter(b -> b.getDueDate().isAfter(today.minusDays(1)))
                    .sorted(Comparator.comparing(Bill::getDueDate))
                    .toList();

            userBills.put(user, bills);
        }

        model.addAttribute("userBills", userBills);

        return "index";
    }
}
