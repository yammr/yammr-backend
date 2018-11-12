package com.gibgab.service.database.repository;

import com.gibgab.service.database.entity.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Integer> {
    VerificationToken findByVerificationToken(String token);
}
