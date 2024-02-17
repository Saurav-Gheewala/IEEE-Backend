package com.example.FinalRegistartion.controller;

import com.example.FinalRegistartion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/demo")
public class Controller
{
    @Autowired
    UserService userService;
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            userService.processCSVAndSendEmail(file);
            return "File uploaded and processed successfully!";
        } catch (IOException e) {
            return "Error processing file: " + e.getMessage();
        }
    }
}

