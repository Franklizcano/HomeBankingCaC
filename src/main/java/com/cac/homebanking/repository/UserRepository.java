package com.cac.homebanking.repository;

import com.cac.homebanking.model.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserBank, UUID> {}