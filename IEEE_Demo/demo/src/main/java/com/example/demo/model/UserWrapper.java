package com.example.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserWrapper
{
    private String name;
    private String email;
    private String phone;
    private String category;

    public UserWrapper(String name, String email, String phone, String category) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.category = category;
    }
}
