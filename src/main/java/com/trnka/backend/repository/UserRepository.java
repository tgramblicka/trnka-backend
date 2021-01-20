package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trnka.backend.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
