package pl.envelo.moovelo.repository.survey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.surveys.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}