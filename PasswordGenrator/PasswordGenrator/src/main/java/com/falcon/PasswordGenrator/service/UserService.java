package com.falcon.PasswordGenrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Random;

@org.springframework.stereotype.Service
public class UserService {
    @Autowired
    JavaMailSender javaMailSender;
//    public void processCSVAndSendEmail(String csvFilePath) throws IOException {
//        try (FileReader fileReader = new FileReader(csvFilePath);
//             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader)) {
//            for (CSVRecord csvRecord : csvParser) {
//                String name = csvRecord.get("name");
//                String email = csvRecord.get("email");
//                String phone = csvRecord.get("phone");
//
//                // Generate a random password
//
//
//                // Send email with random password
//                sendEmail(email, randomPassword);
//
//                // Save user to MySql with the generated password
//                User user = new User();
//                user.setName(name);
//                user.setEmail(email);
//                user.setPhone(phone);
//                user.setPassword(randomPassword);  // Save the generated password
//                userRepository.save(user);
//            }
//        }
//    }

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

    public void sendEmail(String to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String password = generateRandomPassword();
        mailMessage.setTo(to);
        mailMessage.setSubject("Your Random Password");
        mailMessage.setText("Your randomly generated password is: " + password);
        javaMailSender.send(mailMessage);
    }
}
