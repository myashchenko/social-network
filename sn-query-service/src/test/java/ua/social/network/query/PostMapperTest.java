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
    public void testGetUserWithoutFriends() {
        PostDto result = postMapper.getSingle(singletonMap("id", "1"));
        assertThat(result, new EntityMatcher<>(expected(null, "TEST")));
    }

    private PostDto expected(String createdDate, String text) {
        PostDto postDto = new PostDto();
        postDto.setCreatedDate(createdDate);
        postDto.setText(text);
        return postDto;
    }
}
