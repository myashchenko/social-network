package ua.social.network;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Mykola Yashchenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CommunityServiceApplication.class)
@WebAppConfiguration
public class CommunityServiceApplicationTests {

    @Test
    public void contextLoads() {
    }
}
