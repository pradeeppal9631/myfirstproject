package com.project.myfirstproject.service;

import com.project.myfirstproject.entity.ClientEntry;
import com.project.myfirstproject.entity.JournalEntry;
import com.project.myfirstproject.repository.Userentryrepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class Userentryservice {

    @Autowired
    private Userentryrepo userentryrepo;
    @Autowired
    private Cliententryservice cliententryservice;
    @Transactional
    public void saveEntry(JournalEntry journalEntry,String clientName) {

        try{
            ClientEntry client = cliententryservice.findByClientName(clientName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = userentryrepo.save(journalEntry);

            client.getJournalEntry().add(saved);
            cliententryservice.setclientName(null);
            cliententryservice.saveEntry(client);
        }
        catch (Exception e){
         System.out.println(e);
        }
        ClientEntry client = cliententryservice.findByClientName(clientName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = userentryrepo.save(journalEntry);

        client.getJournalEntry().add(saved);
        cliententryservice.setclientName(null);
        cliententryservice.saveEntry(client);
    }

     public List<JournalEntry> getAll(){
        return userentryrepo.findAll();

     }

     public Optional<JournalEntry> findById(ObjectId id){
        return userentryrepo.findById(id);
     }

     public void deleteById(ObjectId id, String clientName){
       ClientEntry client = cliententryservice.findByClientName(clientName);
       client.getJournalEntry().removeIf(x->x.getId().equals(id));
       cliententryservice.saveEntry(client);
        userentryrepo.deleteById(id);
     }



    public JournalEntry updateEntry(JournalEntry journalEntry) {
        return userentryrepo.save(journalEntry);
    }


}

// controller --> service_--> repository
