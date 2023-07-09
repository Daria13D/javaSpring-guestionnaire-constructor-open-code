package ru.arharova.questionnarie_constructor.controllers;

import ru.arharova.questionnarie_constructor.models.Questionnaire;
import ru.arharova.questionnarie_constructor.models.User;
import ru.arharova.questionnarie_constructor.projection.Statistics;
import ru.arharova.questionnarie_constructor.service.AdminServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminServ adminServ;
    public AdminController(AdminServ adminServ) {
        this.adminServ = adminServ;
    }

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @PostMapping
    @PreAuthorize("hasAuthority('developers:write')")
    public ResultDTO createQuestionary(@RequestBody Questionnaire questionary) {
        adminServ.addQuestionnaire(questionary);
        return ResultDTO.SUCCESS_RESULT;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResultDTO deleteQuestionaty(@PathVariable long id) {
        adminServ.remove(id);
        return ResultDTO.SUCCESS_RESULT;

    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public ResultDTO editQuestionaty(@PathVariable long id, @RequestBody Questionnaire questionnaire) {
        adminServ.editQuestionnaire(id, questionnaire);
        return ResultDTO.SUCCESS_RESULT;

    }

    @GetMapping("getusers")
    @PreAuthorize("hasAuthority('developers:write')")
    public List<User> findAllUsers() {
        return adminServ.findAllUsers();
    }

    @GetMapping("findUsersStatistics/{questionFirstId}/{questionSecondId}")
    @PreAuthorize("hasAuthority('developers:write')")
    public List<Statistics> findUsersStatistics(@PathVariable long questionFirstId, @PathVariable long questionSecondId) {
        return adminServ.getUsersStatistics(questionFirstId, questionSecondId);
    }
}
