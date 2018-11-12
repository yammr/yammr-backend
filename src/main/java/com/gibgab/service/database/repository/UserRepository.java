package com.gibgab.service.database.repository;

import com.gibgab.service.database.entity.ApplicationUser;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<ApplicationUser, Integer> {
    ApplicationUser findByEmail(String email);
}
