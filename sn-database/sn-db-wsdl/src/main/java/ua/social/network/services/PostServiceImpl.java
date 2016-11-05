package ua.social.network.services;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Mykola Yashchenko
 */
@WebService(targetNamespace = "http://user.services.network.social.ua/", name = "PostService")
public class PostServiceImpl implements PostService {

    @WebMethod(operationName = "CreatePost")
    @WebResult(name = "CreatePostResponse", targetNamespace = "http://user.services.network.social.ua/", partName = "parameters")
    public CreatePostResponse createPost(
            @WebParam(partName = "parameters", name = "CreatePostRequest", targetNamespace = "http://user.services.network.social.ua/")
                    CreatePostRequest parameters
    ) {
        return null;
    }
}
