package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.dao.UserDao;
import com.example.demo.model.UserAuth;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class HomeController
{
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    //http://localhost:8080/quiz/get
    @PostMapping("auth")
    public ResponseEntity<Integer> authUser(@RequestBody UserAuth userAuth)
    {
        String email = userAuth.getEmail();
        String password = userAuth.getPassword();
        String category = userAuth.getCategory();
        boolean userExists = userService.doesUserExistByEmail(email, password);
        System.out.println(email);
        System.out.println(password);
        System.out.println(category);
        System.out.println(userExists);
//        return ResponseEntity.status(HttpStatus.OK).body(userExists);
        if(userExists) {
            System.out.println("hello");
            return userService.createQuiz(category);
        }
        else
            return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
        return userService.addQuestion(question);
    }



}
