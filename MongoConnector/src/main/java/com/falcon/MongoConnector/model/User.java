package com.falcon.MongoConnector.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "demo")
@Getter
@Setter
@ToString
public class User
{
    @Id
    private int id;
    private String name;
    private String email;
}
