package com.example.unboltsoft.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "node")
public class NodeRoot {

    @Id
    private String id;

    private String name;

    public NodeRoot(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
