package com.falcon.MongoConnector.controller;

import com.falcon.MongoConnector.model.User;
import com.falcon.MongoConnector.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class HomeController
{
    @Autowired
    HomeService homeService;

    @PostMapping("add")
    public ResponseEntity<String> addUser(@RequestBody User user)
    {
        return homeService.addUser(user);
    }
}
