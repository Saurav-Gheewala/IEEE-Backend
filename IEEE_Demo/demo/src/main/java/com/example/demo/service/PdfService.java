package com.example.demo.service;
import com.example.demo.dao.QuizDao;
import com.example.demo.model.Quiz;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PdfService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    EmailService emailService;
    public void PdfUpdateAndSend(Integer id) throws Exception {
        Quiz quiz = quizDao.findById(id).get();

        String name = quiz.getName();
        String email = quiz.getEmail();
        float x = 300;
        float y = 220;
        Path pdfPath = Paths.get("D:\\Spring_Boot\\Project\\IEEE\\IEEE_Demo\\demo\\src\\main\\resources\\Inspairo_Certifiate.pdf");
        try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
            PDPage page = document.getPage(0); // Access the first (and only) page

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true))
            {
                contentStream.setFont(PDType1Font.TIMES_ITALIC, 25);
                contentStream.lineTo( x - (name.length()*5) ,y);
                contentStream.beginText();
                contentStream.newLineAtOffset(x - (name.length()*5), y);
                contentStream.showText(name);
                contentStream.endText();
            }

            // Convert the modified document to a byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
             byte[] certificate = byteArrayOutputStream.toByteArray();
             emailService.sendMail(email,certificate);
        }
    }
}