package com.yognirog.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yognirog.model.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

	Optional<User> findByEmail(String email);
}
