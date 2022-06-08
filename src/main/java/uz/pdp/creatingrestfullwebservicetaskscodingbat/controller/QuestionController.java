package uz.pdp.creatingrestfullwebservicetaskscodingbat.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Question;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.QuestionDto;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.service.QuestionService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<Page<Question>> getQuestionsController(@RequestParam int page) {
        return questionService.getQuestionsService(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Question> getQuestionController(@PathVariable Integer id) {
        return questionService.getQuestionService(id);
    }

    @PostMapping
    public ResponseEntity<Message> addQuestionController(@Valid @RequestBody QuestionDto questionDto) {
        return questionService.addQuestionService(questionDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editQuestionController(@Valid @RequestBody QuestionDto questionDto, @PathVariable Integer id){
        return questionService.editQuestionService(id,questionDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteQuestionController(@PathVariable Integer id){
        return questionService.deleteQuestionService(id);
    }
}
