package ru.arharova.questionnarie_constructor.repos;

import ru.arharova.questionnarie_constructor.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM Question WHERE questionnaire_id = ?1")
    List<Question> findQuestionsByQuestionnaireId(Long id);

}
