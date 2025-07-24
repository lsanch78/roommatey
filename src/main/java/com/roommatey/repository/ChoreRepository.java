package com.roommatey.repository;

import com.roommatey.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoreRepository extends JpaRepository<Chore, Long> {}
