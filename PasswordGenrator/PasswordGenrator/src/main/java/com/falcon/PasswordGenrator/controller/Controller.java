package com.falcon.PasswordGenrator.controller;

import com.falcon.PasswordGenrator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.User;

import java.io.IOException;

@RestController
@RequestMapping("mail")
public class Controller {
    @Autowired
    UserService userService;
//    @PostMapping("send")
//    public String processCSVAndSendEmail(@RequestParam String csvFilePath) {
//        try {
//            userService.processCSVAndSendEmail(csvFilePath);
//            return "CSV processed successfully!";
//        } catch (IOException e) {
//            return "Error processing CSV: " + e.getMessage();
//        }
//    }
    @GetMapping("send")
    public String sendEmail() throws IOException {
        userService.sendEmail("dv.charkhawala@gmail.com");
        return "Send";
    }
}

