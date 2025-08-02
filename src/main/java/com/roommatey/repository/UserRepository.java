package com.roommatey.repository;

import com.roommatey.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByphoneNumber(String phoneNumber);
}
