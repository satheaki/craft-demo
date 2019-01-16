package com.intuit.craftdemo.repositories;

import com.intuit.craftdemo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    User findUserByUserName(String userName);
}
