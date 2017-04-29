package ua.social.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PostDto {
    private String id;
    private String createDate;

    private String text;
    private UserDto sender;
}
