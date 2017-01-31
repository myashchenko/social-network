package ua.social.network.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.social.network.entity.User;
import ua.social.network.repository.UserRepository;

/**
 * @author Mykola Yashchenko
 */
@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @HystrixCommand
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
