package ua.social.network.communityqueryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author Mykola Yashchenko
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PostDto {
    private String id;
    private String createdDate;
    private String text;
}
