package com.example.demo.service;

import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.QuizDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<Integer> createQuiz(String category, String email) {
        User user = userDao.findByEmail(email);
        int numQ = 10;
        String title = "IEEE_Quiz";
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quiz.setName(user.getName());
        quiz.setEmail(email);
        quiz.setPhone(user.getPhone());
        quiz.setCategory(category);
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

    public ResponseEntity<UserWrapper> userDetails(Integer id)
    {
        Optional<Quiz> quiz = quizDao.findById(id);
        UserWrapper userWrapper = new UserWrapper(quiz.get().getName(),quiz.get().getEmail(),quiz.get().getPhone(),quiz.get().getCategory());
        return  new ResponseEntity<>(userWrapper, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestions(Integer quizId)
    {
        Optional<Quiz> quiz = quizDao.findById(quizId);
        List<Question> questionsFromDB = quiz.get().getQuestion();
        List<QuestionWrapper> questionsFroUser = new ArrayList<>();
        for(Question q : questionsFromDB)
        {
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsFroUser.add(qw);
        }

        return new ResponseEntity<>(questionsFroUser,HttpStatus.OK);
    }
}
