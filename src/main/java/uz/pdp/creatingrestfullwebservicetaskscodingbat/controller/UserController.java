package uz.pdp.creatingrestfullwebservicetaskscodingbat.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.User;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsersController(@RequestParam int page) {
        return userService.getUsersService(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserController(@PathVariable Integer id) {
        return userService.getUserService(id);
    }

    @PostMapping
    public ResponseEntity<Message> addUserController(@Valid @RequestBody User user) {
        return userService.addUserService(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editUserController(@Valid @RequestBody User user, @PathVariable Integer id) {
        return userService.editUserService(id,user);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteUserController(@PathVariable Integer id) {
        return userService.deleteUserService(id);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
