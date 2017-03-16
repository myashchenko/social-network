package ua.social.network.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.social.network.config.QueryConfig;
import ua.social.network.dto.PostDto;
import ua.social.network.matcher.EntityMatcher;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertThat;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {QueryConfig.class})
@Sql(scripts = "classpath:post/posts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:truncate_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    public void testGetPost() {
        PostDto result = postMapper.getSingle(singletonMap("id", "1"));
        assertThat(result, new EntityMatcher<>(expected("1", null, "TEST1")));
    }

    @Test
    public void testGetPostListByUserId() {
        List<PostDto> result = postMapper.getList(singletonMap("userId", "post-user-1"));
        assertThat(result, new EntityMatcher<>(asList(expected("2", null, "TEST2"))));
    }


    @Test
    public void testGetPostListByCommunityId() {
        List<PostDto> result = postMapper.getList(singletonMap("communityId", "post-community-1"));
        assertThat(result, new EntityMatcher<>(asList(expected("3", null, "TEST3"))));
    }

    private PostDto expected(String id, String createdDate, String text) {
        PostDto postDto = new PostDto();
        postDto.setId(id);
        postDto.setCreatedDate(createdDate);
        postDto.setText(text);
        return postDto;
    }
}
