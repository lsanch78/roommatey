package com.roommatey.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private BillCategory category;

    private Double amount;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    private boolean recurring;

    public boolean isRecurring() { return recurring; }
    public void setRecurring(boolean recurring) { this.recurring = recurring; }

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private java.util.List<BillShare> shares = new java.util.ArrayList<>();

    public List<BillShare> getShares() { return shares; }
    public void setShares(List<BillShare> shares) { this.shares = shares; }


    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BillCategory getCategory() { return category; }
    public void setCategory(BillCategory category) { this.category = category; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Household getHousehold() { return household; }
    public void setHousehold(Household household) { this.household = household; }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
