package com.example.demo.service;

import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.QuizDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

@org.springframework.stereotype.Service
public class UserService
{
    @Autowired
    UserDao userDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    QuizDao quizDao;

    public ResponseEntity<Integer> authAndCreateQuiz(UserAuth userAuth)
    {
        boolean userExists = doesUserExistByEmail(userAuth.getEmail(), userAuth.getPassword());
        if(userExists)
            return createQuiz(userAuth.getCategory(),userAuth.getEmail());
        else
            return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
    }
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
        quiz.setScore(0);
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

    public ResponseEntity<Integer> calculateResult(Integer id, Map<Integer, String> responses)
    {
        List<QuizResponse> res = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : responses.entrySet()) {
            Integer questionId = entry.getKey();
            String userResponse = entry.getValue();

            QuizResponse quizResponse = new QuizResponse(questionId, userResponse);
            res.add(quizResponse);
        }
        int right = calculate(id, res);
        Optional<Quiz> optionalQuiz = quizDao.findById(id);
        Quiz quiz = optionalQuiz.get();
        quiz.setScore(right);
        quizDao.save(quiz);
        System.out.println(right);
        return  new ResponseEntity<>(right,HttpStatus.OK);
    }
    private int calculate(Integer id, List<QuizResponse> responses)
    {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestion();
        System.out.println(responses);
        System.out.println("");
        System.out.println(questions);
        System.out.println("");
        questions.sort(Comparator.comparingInt(Question::getId));
        System.out.println(questions);

        int right = 0;
        int i = 0;
        for(QuizResponse response : responses)
        {
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
            {
                right++;
            }
            i++;
        }
    return right;
    }

    public ResponseEntity<ResultWrapper> getdetails(Integer id)
    {
        Quiz quiz = quizDao.findById(id).get();
        ResultWrapper resultWrapper =new ResultWrapper(quiz.getName(),quiz.getEmail(),quiz.getPhone(),quiz.getCategory(),quiz.getScore());
        return new ResponseEntity<>(resultWrapper,HttpStatus.OK);
    }

    public ResponseEntity<Optional<Quiz>>   getData(Integer id)
    {
        return new ResponseEntity<>(quizDao.findById(id), HttpStatus.OK);
    }
}
