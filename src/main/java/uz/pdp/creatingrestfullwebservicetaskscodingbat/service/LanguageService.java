package uz.pdp.creatingrestfullwebservicetaskscodingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.Language;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.LanguageRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class LanguageService {
    final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public ResponseEntity<Page<Language>> getLanguagesService(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.status(HttpStatus.OK).body(languageRepository.findAll(pageable));
    }

    public ResponseEntity<Language> getLanguageService(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.map(language -> ResponseEntity.status(HttpStatus.OK).body(language)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addLanguageService(Language language) {
        if (!Objects.equals(language.getName(), "")) {
            boolean exists = languageRepository.existsByName(language.getName());
            if (!exists) {
                languageRepository.save(new Language(language.getName()));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The language has been successfully added!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "This language is already exists!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(false, "Please enter language name fully!"));
        }
    }

    public ResponseEntity<Message> editLanguageService(Integer id, Language language) {
        if (!Objects.equals(language.getName(), "")) {
            Optional<Language> optionalLanguage = languageRepository.findById(id);
            if (optionalLanguage.isPresent()) {
                Language editingLanguage = optionalLanguage.get();
                editingLanguage.setName(language.getName());
                languageRepository.save(editingLanguage);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The language has been successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The language that you want to edit has not been found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Please enter language's name fully!"));
        }
    }

    public ResponseEntity<Message> deleteLanguageService(Integer id) {
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (optionalLanguage.isPresent()) {
            languageRepository.delete(optionalLanguage.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true,"The language has been successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false,"The language that you want to delete has not been found!"));
        }
    }
}
