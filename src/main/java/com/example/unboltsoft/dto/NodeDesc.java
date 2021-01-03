package com.example.unboltsoft.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "node")
public class NodeDesc {

    @Id
    private String id;

    private String name;
    private String description;

    public NodeDesc(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
