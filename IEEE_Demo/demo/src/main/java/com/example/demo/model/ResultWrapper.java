package com.example.demo.model;

import lombok.Data;

@Data
public class ResultWrapper {
    private String name;
    private String email;
    private String phone;
    private String category;
    private Integer score;

    public ResultWrapper(String name, String email, String phone, String category, Integer score) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.category = category;
        this.score = score;
    }
}
