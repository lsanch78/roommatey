package com.roommatey.model;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDate;


@Entity
public class Chore {


    private LocalDate startDate; // The day the chore begins

    @Enumerated(EnumType.STRING)
    private RepeatType repeatType; // DAILY, WEEKLY, MONTHLY

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private ChoreType type;

    private Integer recurringDayOfMonth;

    @Enumerated(EnumType.STRING)
    private DayOfWeek recurringDayOfWeek;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String instructions;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedTo;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }

    public Household getHousehold() { return household; }
    public void setHousehold(Household household) { this.household = household; }

    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User assignedTo) { this.assignedTo = assignedTo; }

    public Integer getRecurringDayOfMonth() {
        return recurringDayOfMonth;
    }

    public void setRecurringDayOfMonth(Integer recurringDayOfMonth) {
        this.recurringDayOfMonth = recurringDayOfMonth;
    }

    public DayOfWeek getRecurringDayOfWeek() {
        return recurringDayOfWeek;
    }

    public void setRecurringDayOfWeek(DayOfWeek recurringDayOfWeek) {
        this.recurringDayOfWeek = recurringDayOfWeek;
    }
    public ChoreType getType() {
        return type;
    }
    public void setType(ChoreType type) {
        this.type = type;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public RepeatType getRepeatType() {
        return repeatType;
    }
    public void setRepeatType(RepeatType repeatType) {
        this.repeatType = repeatType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
