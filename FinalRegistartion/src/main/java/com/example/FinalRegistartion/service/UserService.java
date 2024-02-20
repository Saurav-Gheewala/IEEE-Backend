package com.example.FinalRegistartion.service;

import com.example.FinalRegistartion.model.User;
import com.example.FinalRegistartion.repository.UserRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Random;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    public void processCSVAndSendEmail(MultipartFile file) throws IOException {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            for (CSVRecord csvRecord : csvParser) {
                String name = csvRecord.get("name");
                String email = csvRecord.get("email");
                String phone = csvRecord.get("phone");

                String randomPassword = generateRandomPassword();

                sendEmail(email, randomPassword);

                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPhone(phone);
                user.setPassword(randomPassword);
                userRepository.save(user);
            }
        }
    }


    private String generateRandomPassword() {
        // Generate a random password logic (customize as needed)
        // This is a simple example; you may want to use a more secure method
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
    public void sendEmail(String to, String password) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Welcome To Spectrum'24");
        mailMessage.setText("Your Password For Spectrum'24 IEEE Quiz Is " + password );
        javaMailSender.send(mailMessage);
        System.out.println("Sent");
    }

}
