package ru.arharova.questionnarie_constructor.service.impl;

import org.hibernate.annotations.Filter;
import ru.arharova.questionnarie_constructor.exception.NoSuchCountExeption;
import ru.arharova.questionnarie_constructor.models.Questionnaire;
import ru.arharova.questionnarie_constructor.models.User;
import ru.arharova.questionnarie_constructor.projection.Statistics;
import ru.arharova.questionnarie_constructor.repos.AnswerRepo;
import ru.arharova.questionnarie_constructor.repos.QuestionnaireRepo;
import ru.arharova.questionnarie_constructor.repos.UserRepo;
import ru.arharova.questionnarie_constructor.service.AdminServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;


@Service
public class ServiceImplAdmin implements AdminServ {
    private UserRepo userRepo;
    @Autowired
    public void setReposUser(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    private QuestionnaireRepo questionnaireRepo;
    @Autowired
    public void setReposUser(QuestionnaireRepo questionnaireRepo) {
        this.questionnaireRepo = questionnaireRepo;
    }

    private AnswerRepo answerRepo;
    @Autowired
    public void setReposUser(AnswerRepo answerRepo) {
        this.answerRepo = answerRepo;
    }


    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void remove(long id) {
        questionnaireRepo.deleteById(id);
    }

    @Override
    public void addQuestionnaire(Questionnaire questionnaire) {
        questionnaireRepo.save(questionnaire);
    }

    @Override
    public void editQuestionnaire(long id, Questionnaire newQcuestionnaire) {
        questionnaireRepo.findById(id).map(questionnaire -> {
            questionnaire.setTitle(newQcuestionnaire.getTitle());
            for(int i = 0; i < questionnaire.getQuestions().size(); i ++) {
                questionnaire.getQuestions().get(i).setText(newQcuestionnaire.getQuestions().get(i).getText());
                questionnaire.getQuestions().get(i).setRightAnswerIdx(newQcuestionnaire.getQuestions().get(i).getRightAnswerIdx());

                for (int j = 0; j < questionnaire.getQuestions().get(i).getAnswers().size(); j++) {
                    questionnaire.getQuestions().get(i).getAnswers().get(j).setText(newQcuestionnaire.getQuestions().get(i).getAnswers().get(j).getText());
                }
            }
            return questionnaireRepo.save(questionnaire);
        }).orElseThrow(() -> new NoSuchCountExeption("No id"));
    }

    public List<Statistics> getUsersStatistics(long questionFirstId, long questionSecondId) {
        return answerRepo.findUsersStatistics(questionFirstId, questionSecondId);
    }

}
