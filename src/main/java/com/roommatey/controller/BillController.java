package com.roommatey.controller;

import com.roommatey.model.*;
import com.roommatey.repository.BillRepository;
import com.roommatey.repository.HouseholdRepository;
import com.roommatey.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.roommatey.repository.UserRepository;


import java.util.List;

@Controller
@RequestMapping("/bills")
public class BillController {

    private final BillRepository billRepo;
    private final HouseholdRepository householdRepo;
    private final UserRepository userRepo;

    public BillController(BillRepository billRepo, HouseholdRepository householdRepo, UserRepository userRepo) {
        this.billRepo = billRepo;
        this.householdRepo = householdRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/new")
    public String newBillForm(Model model) {
        model.addAttribute("bill", new Bill());
        model.addAttribute("households", householdRepo.findAll());
        model.addAttribute("categories", BillCategory.values());
        model.addAttribute("users", userRepo.findAll());
        return "create-bill";
    }

    @PostMapping
    public String createBill(@ModelAttribute Bill bill,
                             @RequestParam("userIds") List<Long> userIds,
                             @RequestParam("splitType") String splitType) {

        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        bill.setHousehold(household);
        billRepo.save(bill); // save first so bill has ID

        List<User> users;

        if (splitType.equals("even")) {
            // Grab all users in the household
            users = userRepo.findAll().stream()
                    .filter(u -> u.getHousehold().getId().equals(household.getId()))
                    .toList();

            double splitAmount = bill.getAmount() / users.size();

            for (User user : users) {
                BillShare share = new BillShare();
                share.setUser(user);
                share.setBill(bill);
                share.setAmountOwed(splitAmount);
                bill.getShares().add(share);
            }

        } else if (splitType.equals("single")) {
            // Use selected checkbox (must only pick one)
            users = userRepo.findAllById(userIds);
            User user = users.get(0);

            BillShare share = new BillShare();
            share.setUser(user);
            share.setBill(bill);
            share.setAmountOwed(bill.getAmount());
            bill.getShares().add(share);
        }


        billRepo.save(bill);
        return "redirect:/bills/all";
    }



    @GetMapping("/all")
    public String listBills(Model model) {
        List<Bill> bills = billRepo.findAll();
        model.addAttribute("bills", bills);
        return "bill-list";
    }
}
