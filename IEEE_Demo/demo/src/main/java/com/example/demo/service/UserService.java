package com.example.demo.service;

import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.QuizDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@org.springframework.stereotype.Service
public class UserService
{
    @Autowired
    UserDao userDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuizDao quizDao;
    public boolean doesUserExistByEmail(String email, String password) {
        return userDao.existsByEmailAndPassword(email, password);
    }

    public ResponseEntity<Integer> createQuiz(String category) {
        int numQ = 10;
        String title = "IEEE_Quiz";
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizDao.save(quiz);
        Quiz savedQuiz = quizDao.save(quiz);
        Integer generatedId = savedQuiz.getId();
        return new ResponseEntity<>(generatedId, HttpStatus.CREATED);
    }


    public ResponseEntity<String> addQuestion(Question question)
    {
        questionDao.save(question);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}
