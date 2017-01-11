package ua.social.network.query;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.social.network.config.QueryConfig;
import ua.social.network.dto.UserDto;
import ua.social.network.matcher.EntityMatcher;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertThat;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {QueryConfig.class})
@Sql(scripts = "classpath:user/users.sql")
public class UserMapperTest extends AbstractMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetUserWithoutFriends() {
        UserDto result = userMapper.getUser(singletonMap("id", "1"));
        assertThat(result, new EntityMatcher<>(expected("USER-1", emptyList())));
    }

    @Test
    public void testGetUserWithFriends() {
        UserDto result = userMapper.getUser(singletonMap("id", "2"));
        assertThat(result, new EntityMatcher<>(expected("USER-2", asList(expected("USER-3"), expected("USER-4")))));
    }

    @Test
    public void testGetUserWhichDoesNotExist() {
        UserDto user = userMapper.getUser(singletonMap("id", "bad_id"));
        assertThat(user, Matchers.nullValue());
    }

    private UserDto expected(String name) {
        return expected(name, null);
    }

    private UserDto expected(String name, List<UserDto> friends) {
        UserDto expected = new UserDto();
        expected.setName(name);
        expected.setFriends(friends);
        return expected;
    }
}
