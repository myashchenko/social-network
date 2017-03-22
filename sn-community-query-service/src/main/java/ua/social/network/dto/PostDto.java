package ua.social.network.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@ToString
public class PostDto {
    private String id;
    private String createdDate;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostDto postDto = (PostDto) o;

        if (id != null ? !id.equals(postDto.id) : postDto.id != null) return false;
        if (createdDate != null ? !createdDate.equals(postDto.createdDate) : postDto.createdDate != null) return false;
        return text != null ? text.equals(postDto.text) : postDto.text == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
