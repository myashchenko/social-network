package ua.social.network.communityqueryservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author Mykola Yashchenko
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommunityDto {
    private String name;
    private String description;
    private String userId;
    private List<String> followers;
    private List<PostDto> posts;
}
