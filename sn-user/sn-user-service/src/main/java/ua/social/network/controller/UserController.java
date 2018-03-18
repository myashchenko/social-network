package ua.social.network.controller;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ua.social.network.dto.CreateUserRequest;
import ua.social.network.entity.File;
import ua.social.network.entity.Role;
import ua.social.network.entity.User;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.repository.FileRepository;
import ua.social.network.repository.UserRepository;
import ua.social.network.service.StorageService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final StorageService storageService;
    private final FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserController(UserRepository userRepository, StorageService storageService, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.fileRepository = fileRepository;
    }

    @PostMapping
    public void createUser(@Valid @RequestBody CreateUserRequest createUserRequest, Principal principal) {
        if (principal != null) {
            throw new AccessDeniedException("Only unauthorized user can do registration");
        }
        // todo use mapper
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setName(createUserRequest.getName());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @PostMapping("/{id}/avatar")
    public void uploadAvatar(Principal principal, @RequestParam("file") MultipartFile multipartFile) {
        String fullPath = storageService.store(multipartFile);

        File file = new File();
        file.setUserId(principal.getName());
        file.setFilePath(fullPath);

        fileRepository.save(file);
    }
}