package ru.arharova.questionnarie_constructor.scores;

import ru.arharova.questionnarie_constructor.projection.UserView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
public class UserScore {
    private UserView user;

    private Integer score;

    public UserScore(UserView user, Integer score) {
        this.user = user;
        this.score = score;
    }
}
