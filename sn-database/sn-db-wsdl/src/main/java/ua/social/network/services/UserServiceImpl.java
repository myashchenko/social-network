package ua.social.network.services;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ua.social.network.entity.User;
import ua.social.network.repository.UserRepository;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Mykola Yashchenko
 */
@WebService(targetNamespace = "http://user.services.network.social.ua/", name = "UserService")
public class UserServiceImpl extends AbstractService implements UserService {

    private MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();

    @Autowired
    private UserRepository userRepository;

    @WebMethod(operationName = "CreateUser")
    @WebResult(name = "response", partName = "response")
    public CreateUserResponse createUser(@WebParam(partName = "request", name = "request") CreateUserRequest request) {
        return process(() -> {
            User user = mapper.map(request.getUserData(), User.class);
            userRepository.save(user);
            CreateUserResponse createUserResponse = processSuccess(CreateUserResponse.class);
            createUserResponse.setId(user.getId());
            return createUserResponse;
        }, CreateUserResponse.class);
    }

    @WebMethod(operationName = "UpdateUser")
    @WebResult(name = "response", partName = "response")
    public UpdateUserResponse updateUser(@WebParam(partName = "request", name = "request") UpdateUserRequest request) {
        return process(() -> {
            User user = mapper.map(request.getUserData(), User.class);
            userRepository.save(user);
            return processSuccess(UpdateUserResponse.class);
        }, UpdateUserResponse.class);
    }
}
