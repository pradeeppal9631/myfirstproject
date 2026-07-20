package com.project.myfirstproject.repository;

import com.project.myfirstproject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Userentryrepo extends MongoRepository<JournalEntry, ObjectId> {

}