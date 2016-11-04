package ua.social.network.services.user;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Mykola Yashchenko
 */
@WebService(targetNamespace = "http://user.services.network.social.ua/", name = "UserService")
public class UserServiceImpl implements UserService {

    @WebMethod(operationName = "CreateUser")
    @WebResult(name = "CreateUserResponse", targetNamespace = "http://user.services.network.social.ua/", partName = "parameters")
    public CreateUserResponse createUser(
            @WebParam(partName = "parameters", name = "CreateUserRequest",
                    targetNamespace = "http://user.services.network.social.ua/") CreateUserRequest parameters) {
        return null;
    }
}
