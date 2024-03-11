package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.dao.UserDao;
//import com.example.demo.service.MailService;
import com.example.demo.service.EmailService;
import com.example.demo.service.PdfService;
import com.example.demo.service.UserService;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class HomeController
{

    Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    UserService userService;
    @Autowired
    PdfService pdfService;
    @Autowired
    UserDao userDao;

    //http://localhost:8080/quiz/auth
    @PostMapping("auth")
    public ResponseEntity<Integer> authUser(@RequestBody UserAuth userAuth) { return userService.authAndCreateQuiz(userAuth); }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) { return userService.addQuestion(question); }
    @GetMapping("getDetail/{id}")
    public ResponseEntity<UserWrapper> getUserDetails(@PathVariable Integer id)
    {
        return userService.userDetails(id);
    }
    @GetMapping("question/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> Questions(@PathVariable Integer quizId) { return userService.getQuestions(quizId); }
    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody Map<Integer, String> responses) { return userService.calculateResult(quizId,responses);}
    @GetMapping("getLastPage/{id}")
    public ResponseEntity<ResultWrapper> getLastPageDetails(@PathVariable Integer id)  { return userService.getdetails(id);}
    @GetMapping("certificate/{id}")
    public void senCertificate(@PathVariable Integer id) throws Exception  { pdfService.PdfUpdateAndSend(id); }
    @GetMapping("dim/{id}")
    public ResponseEntity<Optional<Quiz>> getQuiz(@PathVariable Integer id)
    {
        return userService.getData(id);
    }

}

