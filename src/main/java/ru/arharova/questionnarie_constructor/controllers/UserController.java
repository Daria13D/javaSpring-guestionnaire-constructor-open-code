package ru.arharova.questionnarie_constructor.controllers;

import ru.arharova.questionnarie_constructor.scores.QuestionnaireScore;
import ru.arharova.questionnarie_constructor.scores.UserScore;
import ru.arharova.questionnarie_constructor.exception.NoSuchCountExeption;
import ru.arharova.questionnarie_constructor.models.Questionnaire;
import ru.arharova.questionnarie_constructor.projection.QuestionnaireView;
import ru.arharova.questionnarie_constructor.service.impl.ServiceImplUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "questionnaire")
public class UserController {
    @Autowired
    ServiceImplUser userService;

    @GetMapping
    public List<QuestionnaireView> getQuestionnaireList() {
        return userService.findAllQuestionnaire();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public Questionnaire getQuestionaryById(@PathVariable Long id) {
        if (userService.getQuestionnaireById(id) == null)
            throw new NoSuchCountExeption("incorrect Data");
        else
            return userService.getQuestionnaireById(id);


    }


    @PostMapping("{userId}/{questionnaireId}")
    @PreAuthorize("hasAuthority('developers:read')")
    public void saveAnswers(@PathVariable Long userId,
                            @PathVariable Long questionnaireId,
                            @RequestBody List<Integer> answers) {
        userService.saveAnswers(answers, questionnaireId, userId);
    }

    @PutMapping("{userId}/{questionnaireId}")
    @PreAuthorize("hasAuthority('developers:read')")
    public void updateAnswers(@PathVariable Long userId,
                              @PathVariable Long questionnaireId,
                              @RequestBody List<Integer> answers) {
        userService.updateAnswers(answers, questionnaireId, userId);
    }

    @GetMapping("userscore/{userId}")
    @PreAuthorize("hasAuthority('developers:read')")
    List<QuestionnaireScore> getUserScoreInAllQuestionnaires(@PathVariable Long userId) {
        return userService.getUserScoreInAllQuestionnaires(userId);
    }

    @GetMapping("getNoCompletedQ/{id}")
    public List<QuestionnaireView> getQuestionnaireListThatUserNoCompleted(@PathVariable Long id) {
        return userService.findQuestionnairesNoCompletedByUser(id);
    }
    @GetMapping("leaderBoard/{id}")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<UserScore> getLeaderBoard(@PathVariable Long id) {
        return userService.getLeaderBordInOneQuestionnaire(id);
    }
}
