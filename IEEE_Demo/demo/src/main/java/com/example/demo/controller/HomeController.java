package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class HomeController
{

    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    //http://localhost:8080/quiz/auth
    @PostMapping("auth")
    public ResponseEntity<Integer> authUser(@RequestBody UserAuth userAuth)
    {
        String email = userAuth.getEmail();
        String password = userAuth.getPassword();
        String category = userAuth.getCategory();
        boolean userExists = userService.doesUserExistByEmail(email, password);
        System.out.println(email);
//        logger.info(password);
        System.out.println(password);
        System.out.println(category);
        System.out.println(userExists);

        if(userExists) {
            System.out.println("hello");
            return userService.createQuiz(category,email);
        }
        else
            return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question)
    {
        return userService.addQuestion(question);
    }
    @GetMapping("getDetail/{id}")
    public ResponseEntity<UserWrapper> getUserDetails(@PathVariable Integer id)
    {
        return userService.userDetails(id);
    }

    @GetMapping("question/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> Questions(@PathVariable Integer quizId)
    {
        return userService.getQuestions(quizId);
    }
    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody Map<Integer, String> responses)
    {
        return userService.calculateResult(quizId,responses);
    }
    @GetMapping("getLastPage/{id}")
    public ResponseEntity<ResultWrapper> getLastPageDetails(@PathVariable Integer id)
    {
        return userService.getdetails(id);
    }

}

