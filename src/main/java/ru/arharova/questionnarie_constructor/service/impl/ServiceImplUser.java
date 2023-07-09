package ru.arharova.questionnarie_constructor.service.impl;

import ru.arharova.questionnarie_constructor.scores.QuestionnaireScore;
import ru.arharova.questionnarie_constructor.scores.UserScore;
import ru.arharova.questionnarie_constructor.models.Answer;
import ru.arharova.questionnarie_constructor.models.Question;
import ru.arharova.questionnarie_constructor.models.Questionnaire;
import ru.arharova.questionnarie_constructor.models.User;
import ru.arharova.questionnarie_constructor.projection.OnlyIdAnswer;
import ru.arharova.questionnarie_constructor.projection.QuestionnaireView;
import ru.arharova.questionnarie_constructor.projection.UserView;
import ru.arharova.questionnarie_constructor.repos.AnswerRepo;
import ru.arharova.questionnarie_constructor.repos.QuestionRepo;
import ru.arharova.questionnarie_constructor.repos.QuestionnaireRepo;
import ru.arharova.questionnarie_constructor.repos.UserRepo;
import ru.arharova.questionnarie_constructor.service.UserServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServiceImplUser implements UserServ {
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
    private UserRepo userRepo;
    @Autowired
    public void setReposUser(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    private QuestionRepo questionRepo;
    @Autowired
    public void setReposUser(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    @Override
    public List<QuestionnaireView> findAllQuestionnaire() {
        return questionnaireRepo.findAllQuestionnairesAsQuestionnaireView();
    }

    @Override
    public List<QuestionnaireView> findQuestionnairesNoCompletedByUser(long userId) {
        return questionnaireRepo.findNoQuestionnaireViewsByUserId(userId);
    }

    @Override
    public void saveAnswers(List<Integer> answers, long questionnaireId, long userId) {
        User user = userRepo.findById(userId).get();

        user.getAnswers().addAll(undestandingUserAnswers(answers, questionnaireId));
        userRepo.saveAndFlush(user);
    }

    @Override
    public void updateAnswers(List<Integer> answers, long questionnaireId, long userId) {
        User user = userRepo.findById(userId).get();

        answerRepo.deleteAnswersInUsersAnswer(userId, questionnaireId);
        user.getAnswers().addAll(undestandingUserAnswers(answers, questionnaireId));
        userRepo.saveAndFlush(user);
    }

    @Override
    public Questionnaire getQuestionnaireById(long questionnaireId) {
        return questionnaireRepo.findById(questionnaireId).get();
    }

    @Override
    public List<UserScore> getLeaderBordInOneQuestionnaire(long questionnaireId) {
        List<UserScore> userScores = new LinkedList<>();
        Questionnaire questionnaire = questionnaireRepo.findById(questionnaireId).get();

        List<UserView> users = userRepo.findUsersByQuestionnaireId(questionnaireId);

        List<UserScore> finalUserScores = userScores;
        users.forEach(appUser -> finalUserScores.add(new UserScore(appUser, getUserScoreInOneQuestionnaire(
                getUserAnswersInOneQuestionnaire(appUser.getId(), questionnaireId), questionnaire))));
        userScores = finalUserScores.stream()
                .sorted(Comparator.comparingInt(UserScore::getScore)
                        .reversed())
                .collect(Collectors.toList());

        return userScores;
    }

    @Override
    public List<QuestionnaireScore> getUserScoreInAllQuestionnaires(long userId) {
        List<QuestionnaireScore> questionnaireScores = new LinkedList<>();
        List<Questionnaire> questionnaires = questionnaireRepo.findAllQuestionnairesByUserId(userId);

        for(Questionnaire questionnaire : questionnaires) {
            questionnaireScores.add(new QuestionnaireScore(questionnaire,  getUserScoreInOneQuestionnaire(
                        getUserAnswersInOneQuestionnaire(userId, questionnaire.getId()), questionnaire)));
        }
        return questionnaireScores;
    }

    @Override
    public List<OnlyIdAnswer> getUserAnswersInOneQuestionnaire(long userId, long questionnaireId) {
        return answerRepo.findAnswersInOneQuestionnaireByUserId(userId, questionnaireId);
    }

    @Override
    public int getUserScoreInOneQuestionnaire(List<OnlyIdAnswer> answers, Questionnaire questionnaire) {
        int score = 0;
        for(Question question : questionnaire.getQuestions()) {
            if(answers.stream().anyMatch(answer -> answer.getId() == question.getAnswers().get(question.getRightAnswerIdx()).getId())) {
                score++;
            }
        }
        return score;
    }

    @Override
    public List<Answer> undestandingUserAnswers(List<Integer> answers, long questionnaireId) {
        List<Question> questions = questionRepo.findQuestionsByQuestionnaireId(questionnaireId);

        List<Answer> usersAnswers = new LinkedList<>();

        for(int i = 0; i < questions.size(); i++) {
            List<Answer> answersInOneQuestion = questions.get(i).getAnswers();
            usersAnswers.add(answersInOneQuestion.get(answers.get(i)));
        }
        return usersAnswers;
    }
}
