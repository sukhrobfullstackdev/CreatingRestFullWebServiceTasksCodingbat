package uz.pdp.creatingrestfullwebservicetaskscodingbat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Answer;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Question;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.User;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.AnswerDto;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.AnswerRepository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.QuestionRepository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class AnswerService {
    final AnswerRepository answerRepository;
    final UserRepository userRepository;
    final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<Page<Answer>> getAnswersController(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.status(HttpStatus.OK).body(answerRepository.findAll(pageable));
    }

    public ResponseEntity<Answer> getAnswerController(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        return optionalAnswer.map(answer -> ResponseEntity.status(HttpStatus.OK).body(answer)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addAnswerController(AnswerDto answerDto) {
        if (!Objects.equals(answerDto.getText(), "") && !Objects.equals(answerDto.getUserId(), 0) && !Objects.equals(answerDto.getQuestionId(), 0)) {
            Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());
            Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
            if (optionalUser.isPresent() && optionalQuestion.isPresent()) {
                answerRepository.save(new Answer(answerDto.getText(), optionalQuestion.get(), answerDto.isCorrect(), optionalUser.get()));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The answer has been successfully added!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The user or question has not been found to assign them to this answer!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Please fill all fields fully!"));
        }
    }

    public ResponseEntity<Message> editAnswerController(Integer id, AnswerDto answerDto) {
        if (!Objects.equals(answerDto.getText(), "") && !Objects.equals(answerDto.getUserId(), 0) && !Objects.equals(answerDto.getQuestionId(), 0)) {
            Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestionId());
            Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
            Optional<Answer> optionalAnswer = answerRepository.findById(id);
            if (optionalAnswer.isPresent() && optionalUser.isPresent() && optionalQuestion.isPresent()) {
                Answer answer = optionalAnswer.get();
                answer.setText(answerDto.getText());
                answer.setCorrect(answerDto.isCorrect());
                answer.setQuestion(optionalQuestion.get());
                answer.setUser(optionalUser.get());
                answerRepository.save(answer);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The answer has been successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The answer or user or question has not been found to edit!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Please fill all fields fully!"));
        }
    }

    public ResponseEntity<Message> deleteAnswerController(Integer id) {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            answerRepository.delete(optionalAnswer.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The answer has been successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The answer has not been found to delete!"));
        }
    }
}
