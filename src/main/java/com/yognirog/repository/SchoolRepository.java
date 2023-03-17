package com.yognirog.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.yognirog.model.School;

public interface SchoolRepository extends MongoRepository<School, ObjectId>{

}
