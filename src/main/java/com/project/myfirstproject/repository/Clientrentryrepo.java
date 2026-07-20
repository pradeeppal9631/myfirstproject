package com.project.myfirstproject.repository;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Clientrentryrepo extends MongoRepository<ClientEntry, ObjectId> {

   public ClientEntry findByClientName(String clientName);

     public void deleteByClientName(String clientName);

}