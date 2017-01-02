package ua.social.network.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.social.network.config.QueryConfig;
import ua.social.network.dto.UserDto;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { QueryConfig.class })
public class UserMapperTest extends AbstractMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetUser() {
        UserDto result = userMapper.getUser(Collections.<String, Object>singletonMap("id", "1"));
        assertNotNull(result);
    }
}
