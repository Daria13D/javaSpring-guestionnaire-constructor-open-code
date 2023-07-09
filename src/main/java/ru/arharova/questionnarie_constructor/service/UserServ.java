package ru.arharova.questionnarie_constructor.service;

import ru.arharova.questionnarie_constructor.scores.QuestionnaireScore;
import ru.arharova.questionnarie_constructor.scores.UserScore;
import ru.arharova.questionnarie_constructor.models.Answer;
import ru.arharova.questionnarie_constructor.models.Questionnaire;
import ru.arharova.questionnarie_constructor.projection.OnlyIdAnswer;
import ru.arharova.questionnarie_constructor.projection.QuestionnaireView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServ {
    List<QuestionnaireView> findAllQuestionnaire();
    List<QuestionnaireView> findQuestionnairesNoCompletedByUser(long userId);
    void saveAnswers(List<Integer> answers, long questionnaireId, long userId);
    void updateAnswers(List<Integer> answers, long questionnaireId, long userId);
    Questionnaire getQuestionnaireById(long questionnaireId);
    List<UserScore> getLeaderBordInOneQuestionnaire(long questionnaireId);
    List<QuestionnaireScore> getUserScoreInAllQuestionnaires(long userId);

    List<OnlyIdAnswer> getUserAnswersInOneQuestionnaire(long userId, long questionnaireId);
    int getUserScoreInOneQuestionnaire(List<OnlyIdAnswer> answers, Questionnaire questionnaire);
    List<Answer> undestandingUserAnswers(List<Integer> answers, long questionnaireId);
}
