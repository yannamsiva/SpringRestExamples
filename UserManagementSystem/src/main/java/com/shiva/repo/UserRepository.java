package com.shiva.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiva.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByDeletedFalse(); // Fetch non-deleted users
}
