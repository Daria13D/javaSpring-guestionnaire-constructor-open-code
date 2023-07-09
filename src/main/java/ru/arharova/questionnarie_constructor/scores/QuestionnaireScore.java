package ru.arharova.questionnarie_constructor.scores;

import ru.arharova.questionnarie_constructor.models.Questionnaire;
import ru.arharova.questionnarie_constructor.projection.QuestionnaireView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
public class QuestionnaireScore {
    private QuestionnaireView questionnaireView;

    private Integer score;

    public QuestionnaireScore(Questionnaire questionnaire, Integer score) {
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();

        this.questionnaireView = pf.createProjection(QuestionnaireView.class, questionnaire);
        this.score = score;
    }
}
