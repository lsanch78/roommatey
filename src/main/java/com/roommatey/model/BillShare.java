package com.roommatey.model;

import jakarta.persistence.*;

@Entity
public class BillShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Bill bill;

    private Double amountOwed;
    private boolean isPaid = false;

    public BillShare(User user, Bill bill, double amountOwed) {
        this.user = user;
        this.bill = bill;
        this.amountOwed = amountOwed;
    }

    public BillShare() {

    }

    // Getters/setters
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Bill getBill() { return bill; }
    public void setBill(Bill bill) { this.bill = bill; }

    public Double getAmountOwed() { return amountOwed; }
    public void setAmountOwed(Double amountOwed) { this.amountOwed = amountOwed; }

    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
}
