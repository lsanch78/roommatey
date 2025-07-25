package com.roommatey.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Household {

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    private List<User> members;

    public List<User> getMembers() { return members; }
    public void setMembers(List<User> members) { this.members = members; }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
