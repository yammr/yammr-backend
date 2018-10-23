package com.gibgab.service.database;

import org.springframework.data.repository.CrudRepository;
import com.gibgab.service.database.User;


public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
}
