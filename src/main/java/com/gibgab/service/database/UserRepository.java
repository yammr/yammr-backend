package com.gibgab.service.database;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<ApplicationUser, Integer> {
    ApplicationUser findByEmail(String email);
}
