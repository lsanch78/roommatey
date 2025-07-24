package com.roommatey.repository;

import com.roommatey.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    Household findByName(String name);
}
