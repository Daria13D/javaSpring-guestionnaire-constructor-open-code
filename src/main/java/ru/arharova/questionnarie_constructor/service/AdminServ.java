package ru.arharova.questionnarie_constructor.service;

import ru.arharova.questionnarie_constructor.models.Questionnaire;
import ru.arharova.questionnarie_constructor.models.User;
import ru.arharova.questionnarie_constructor.projection.Statistics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminServ {
    List<User> findAllUsers();
    void remove(long id);
    void addQuestionnaire(Questionnaire questionnaire);
    void editQuestionnaire(long id, Questionnaire newQcuestionnaire);
    List<Statistics> getUsersStatistics(long questionFirstId, long questionSecondId);

}
