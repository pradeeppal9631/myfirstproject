package com.project.myfirstproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document (collection = "client_entry")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntry {

    @Id
    private ObjectId id;
    @Indexed (unique = true)
    @NonNull

    private String clientName;
    @NonNull
    private String password;
    private String email;

    @DBRef // to generate a link from none entity to another entity like foreign key // link annotation
    private List<JournalEntry> journalEntry =new ArrayList<>();

    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

}
