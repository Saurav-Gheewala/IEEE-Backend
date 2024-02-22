package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuizResponse
{
    private Integer id;
    private String response;

    public QuizResponse(Integer id, String response) {
        this.id = id;
        this.response = response;
    }
}
