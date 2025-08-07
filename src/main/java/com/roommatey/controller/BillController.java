package com.roommatey.controller;

import com.roommatey.model.*;
import com.roommatey.repository.BillRepository;
import com.roommatey.repository.HouseholdRepository;
import com.roommatey.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @GetMapping("/manage")
    public String manageBills(Model model) {
        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        model.addAttribute("bills", billRepo.findAll());
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("household", household);
        return "bill-manage";
    }

    @GetMapping("/edit/{id}")
    public String editBill(@PathVariable Long id, Model model) {
        Bill bill = billRepo.findById(id).orElseThrow();
        model.addAttribute("bill", bill);
        model.addAttribute("users", userRepo.findAll());
        return "edit-bill";
    }

    @GetMapping("/new")
    public String newBillForm(Model model, RedirectAttributes redirectAttrs) {
        if (userRepo.count() == 0) {
            redirectAttrs.addFlashAttribute("error", "You must add at least one roommate before creating a bill.");
            return "redirect:/users/register";
        }
        model.addAttribute("bill", new Bill());
        model.addAttribute("households", householdRepo.findAll());
        model.addAttribute("categories", BillCategory.values());
        model.addAttribute("users", userRepo.findAll());
        return "bill-create";
    }

    @PostMapping
    public String createBill(@ModelAttribute Bill bill,
                             @RequestParam("userIds") List<Long> userIds,
                             @RequestParam("splitType") String splitType,
                             Model model) {

        Household household = householdRepo.findAll().stream().findFirst().orElse(null);
        bill.setHousehold(household);

        List<User> users = userRepo.findAll().stream()
                .filter(u -> u.getHousehold().getId().equals(household.getId()))
                .toList();

        if (users.isEmpty()) {
            model.addAttribute("error", "No users found in the household. Please add users before creating a bill.");
            model.addAttribute("bill", bill);
            model.addAttribute("households", householdRepo.findAll());
            model.addAttribute("categories", BillCategory.values());
            model.addAttribute("users", users);
            return "bill-create";
        }

        if ("even".equals(splitType)) {
            double splitAmount = bill.getAmount() / users.size();
            for (User user : users) {
                BillShare share = new BillShare(user, bill, splitAmount);
                bill.getShares().add(share);
            }
        } else if ("single".equals(splitType)) {
            if (userIds == null || userIds.isEmpty()) {
                model.addAttribute("error", "Please select a user for single split.");
                model.addAttribute("bill", bill);
                model.addAttribute("users", users);
                model.addAttribute("categories", BillCategory.values());
                return "bill-create";
            }
            User user = userRepo.findById(userIds.get(0)).orElse(null);
            bill.getShares().add(new BillShare(user, bill, bill.getAmount()));
        }

        billRepo.save(bill);
        return "redirect:/bills/all";
    }




    @PostMapping("/update/{id}")
    public String updateBill(@PathVariable Long id,
                             @ModelAttribute Bill updatedBill,
                             @RequestParam("splitType") String splitType,
                             @RequestParam(value = "userIds", required = false) List<Long> userIds,
                             Model model) {

        Bill bill = billRepo.findById(id).orElseThrow();
        bill.setName(updatedBill.getName());
        bill.setAmount(updatedBill.getAmount());
        bill.setDueDate(updatedBill.getDueDate());
        bill.setCategory(updatedBill.getCategory());
        bill.setRecurring(updatedBill.isRecurring());
        bill.getShares().clear();

        List<User> users = userRepo.findAll().stream()
                .filter(u -> u.getHousehold().getId().equals(bill.getHousehold().getId()))
                .toList();

        if ("even".equals(splitType)) {
            if (users.isEmpty()) {
                model.addAttribute("error", "No users in household to split the bill evenly.");
                model.addAttribute("bill", bill);
                model.addAttribute("users", users);
                return "edit-bill";
            }
            double splitAmount = bill.getAmount() / users.size();
            for (User u : users) {
                bill.getShares().add(new BillShare(u, bill, splitAmount));
            }
        } else if ("single".equals(splitType)) {
            if (userIds == null || userIds.isEmpty()) {
                model.addAttribute("error", "Please select a user for single split.");
                model.addAttribute("bill", bill);
                model.addAttribute("users", users);
                return "edit-bill";
            }
            User u = userRepo.findById(userIds.get(0)).orElseThrow();
            bill.getShares().add(new BillShare(u, bill, bill.getAmount()));
        }

        billRepo.save(bill);
        return "redirect:/bills/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteBill(@PathVariable Long id) {
        billRepo.deleteById(id);
        return "redirect:/bills/all";
    }


    @GetMapping("/all")
    public String listBills(Model model) {
        List<Bill> bills = billRepo.findAll();
        model.addAttribute("bills", bills);
        return "bill-list";
    }


}
