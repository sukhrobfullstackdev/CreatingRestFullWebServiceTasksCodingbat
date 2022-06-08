package uz.pdp.creatingrestfullwebservicetaskscodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Answer;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.AnswerDto;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.service.AnswerService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/answer")
public class AnswerController {
    final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<Page<Answer>> getAnswersController(@RequestParam int page) {
        return answerService.getAnswersController(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Answer> getAnswerController(@PathVariable Integer id) {
        return answerService.getAnswerController(id);
    }
    @PostMapping
    public ResponseEntity<Message> addAnswerController(@Valid @RequestBody AnswerDto answerDto){
        return answerService.addAnswerController(answerDto);
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editAnswerController(@PathVariable Integer id,@Valid @RequestBody AnswerDto answerDto){
        return answerService.editAnswerController(id,answerDto);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteAnswerController(@PathVariable Integer id){
        return answerService.deleteAnswerController(id);
    }
}
