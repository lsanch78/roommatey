package com.roommatey.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String phoneNumber;


    private String password;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    // Getters + setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Household getHousehold() { return household; }
    public void setHousehold(Household household) { this.household = household; }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillShare> shares = new ArrayList<>();

    public List<BillShare> getShares() {
        return shares;
    }

    public void setShares(List<BillShare> shares) {
        this.shares = shares;
    }

    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL)
    private List<Chore> chores = new ArrayList<>();

    public List<Chore> getChores() {
        return chores;
    }

}
