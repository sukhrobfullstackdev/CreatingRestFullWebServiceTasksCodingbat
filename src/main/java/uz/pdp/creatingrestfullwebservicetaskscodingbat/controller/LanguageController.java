package uz.pdp.creatingrestfullwebservicetaskscodingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Language;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.service.LanguageService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/language")
public class LanguageController {
    final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public ResponseEntity<Page<Language>> getLanguagesController(@RequestParam int page) {
        return languageService.getLanguagesService(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Language> getLanguageController(@PathVariable Integer id) {
        return languageService.getLanguageService(id);
    }

    @PostMapping
    public ResponseEntity<Message> addLanguageController(@Valid @RequestBody Language language) {
        return languageService.addLanguageService(language);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editLanguageController(@Valid @RequestBody Language language, @PathVariable Integer id) {
        return languageService.editLanguageService(id, language);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteLanguageController(@PathVariable Integer id) {
        return languageService.deleteLanguageService(id);
    }
}
