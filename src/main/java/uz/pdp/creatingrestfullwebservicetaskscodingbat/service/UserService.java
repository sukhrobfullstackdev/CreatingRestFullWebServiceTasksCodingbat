package uz.pdp.creatingrestfullwebservicetaskscodingbat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.entity.User;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.payload.Message;
import uz.pdp.creatingrestfullwebservicetaskscodingbat.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Page<User>> getUsersService(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll(pageable));
    }

    public ResponseEntity<User> getUserService(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addUserService(User user) {
        if (!Objects.equals(user.getEmail(), "") && !Objects.equals(user.getPassword(), "")) {
            boolean exists = userRepository.existsByEmailOrPassword(user.getEmail(), user.getPassword());
            if (!exists) {
                userRepository.save(new User(user.getEmail(), user.getPassword()));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The user has been successfully added!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "This email or password is already in use!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Please enter all fields fully!"));
        }
    }

    public ResponseEntity<Message> editUserService(Integer id, User user) {
        if (!Objects.equals(user.getEmail(), "") && !Objects.equals(user.getPassword(), "")) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                boolean exists = userRepository.existsByEmailOrPasswordAndIdNot(user.getEmail(), user.getPassword(), id);
                if (!exists) {
                    User editingUser = optionalUser.get();
                    editingUser.setEmail(user.getEmail());
                    editingUser.setPassword(user.getPassword());
                    userRepository.save(editingUser);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The user has been successfully edited!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The email or password is already in use!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The user that you want to edit has not been found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Please enter all fields fully!"));
        }
    }

    public ResponseEntity<Message> deleteUserService(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The user has been successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The user that you want to delete has not been found!"));
        }
    }

}
