package pl.envelo.moovelo.service.survey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.surveys.Answer;
import pl.envelo.moovelo.repository.survey.AnswerRepository;
import pl.envelo.moovelo.repository.survey.EventSurveyRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer getAnswerById(Long id) {
        log.info("AnswerService - getAnswerById()");
        Optional<Answer> answerOptional = answerRepository.findById(id);
        if (answerOptional.isEmpty()) {
            throw new NoSuchElementException("No answer with id: " + id);
        }
        log.info("AnswerService - getAnswerById() return {}", answerOptional.get());
        return answerOptional.get();
    }
    public void voteForAnswer(){}
}
