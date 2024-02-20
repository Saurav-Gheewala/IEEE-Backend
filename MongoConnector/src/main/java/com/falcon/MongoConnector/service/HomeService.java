package com.falcon.MongoConnector.service;

import com.falcon.MongoConnector.dao.UserDao;
import com.falcon.MongoConnector.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HomeService
{
    @Autowired
    UserDao userDao;


    public ResponseEntity<String> addUser(User user) {
        userDao.save(user);
        return new ResponseEntity<>("Saved", HttpStatus.CREATED);
    }
}
