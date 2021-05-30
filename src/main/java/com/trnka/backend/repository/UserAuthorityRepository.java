package com.trnka.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trnka.backend.domain.UserAuthority;

@Repository
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
