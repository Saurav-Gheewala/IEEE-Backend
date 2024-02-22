package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String name;
    private String email;
    private String phone;
    private String category;
    private Integer score;
    @ManyToMany
    private List<Question> question;
}
