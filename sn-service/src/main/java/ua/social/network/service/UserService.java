package ua.social.network.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.social.network.repository.UserRepository;
import ua.social.network.request.CreateUserRequest;
import ua.social.network.request.ModifyUserRequest;
import ua.social.network.response.BaseResponse;

/**
 * @author Mykola Yashchenko
 */
@RestController("/users")
public class UserService {

    private UserRepository userRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> create(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> modify(@PathVariable("id") String id, @RequestBody ModifyUserRequest request) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok().build();
    }
}
