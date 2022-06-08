package uz.pdp.creatingrestfullwebservicetaskscodingbat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Example;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Language;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Question;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.QuestionDto;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.ExampleRepository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.LanguageRepository;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.QuestionRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
    final QuestionRepository questionRepository;
    final ExampleRepository exampleRepository;
    final LanguageRepository languageRepository;

    public QuestionService(QuestionRepository questionRepository, ExampleRepository exampleRepository, LanguageRepository languageRepository) {
        this.questionRepository = questionRepository;
        this.exampleRepository = exampleRepository;
        this.languageRepository = languageRepository;
    }

    public ResponseEntity<Page<Question>> getQuestionsService(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.status(HttpStatus.OK).body(questionRepository.findAll(pageable));
    }

    public ResponseEntity<Question> getQuestionService(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        return optionalQuestion.map(question -> ResponseEntity.status(HttpStatus.OK).body(question)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addQuestionService(QuestionDto questionDto) {
        if (!Objects.equals(questionDto.getName(), "") && !Objects.equals(questionDto.getHint(), "") && !Objects.equals(questionDto.getQuestion(), "") && !Objects.equals(questionDto.getHint(), "") && !Objects.equals(questionDto.getSolution(), "") && !Objects.equals(questionDto.getText(), "") && !Objects.equals(questionDto.getMethod(), "")) {
            Optional<Language> optionalLanguage = languageRepository.findById(questionDto.getLanguageId());
            if (optionalLanguage.isPresent()) {
                Example example = exampleRepository.save(new Example(questionDto.getText()));
                Language language = optionalLanguage.get();
                questionRepository.save(new Question(questionDto.getName(), questionDto.getQuestion(), questionDto.getSolution(), questionDto.getMethod(), questionDto.getHint(), questionDto.isHasStar(), language, example));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The question has been successfully added!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The language has not been found to assign a question to it!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(true, "Please fill all fields fully!"));
        }
    }

    public ResponseEntity<Message> editQuestionService(Integer id, QuestionDto questionDto) {
        if (!Objects.equals(questionDto.getName(), "") && !Objects.equals(questionDto.getHint(), "") && !Objects.equals(questionDto.getQuestion(), "") && !Objects.equals(questionDto.getHint(), "") && !Objects.equals(questionDto.getSolution(), "") && !Objects.equals(questionDto.getText(), "") && !Objects.equals(questionDto.getMethod(), "")) {
            Optional<Question> optionalQuestion = questionRepository.findById(id);
            Optional<Language> optionalLanguage = languageRepository.findById(questionDto.getLanguageId());
            if (optionalQuestion.isPresent() && optionalLanguage.isPresent()) {
                Question question = optionalQuestion.get();
                Example example = question.getExample();
                example.setText(questionDto.getText());
                Example savedExample = exampleRepository.save(example);
                Language language = optionalLanguage.get();
                question.setQuestion(questionDto.getQuestion());
                question.setExample(savedExample);
                question.setHasStar(questionDto.isHasStar());
                question.setName(questionDto.getName());
                question.setHint(questionDto.getHint());
                question.setSolution(questionDto.getSolution());
                question.setLanguage(language);
                question.setMethod(questionDto.getMethod());
                questionRepository.save(question);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The question has been successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The question or its language has not been found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(true, "Please fill all fields fully to edit!"));
        }
    }

    public ResponseEntity<Message> deleteQuestionService(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            Example example = question.getExample();
            exampleRepository.delete(example);
            questionRepository.delete(question);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The question has been successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The question has not been found!"));
        }
    }
}
