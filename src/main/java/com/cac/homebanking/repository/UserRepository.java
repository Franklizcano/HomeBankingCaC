package com.cac.homebanking.repository;

import com.cac.homebanking.model.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserBank, Long> {}