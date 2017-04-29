package ua.social.network.service;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.social.network.entity.Role;
import ua.social.network.entity.User;
import ua.social.network.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Mykola Yashchenko
 */
public class SnUserDetailsServiceTest {

    @InjectMocks
    private SnUserDetailsService service;

    @Mock
    private UserRepository repository;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldLoadByUsernameWhenUserExists() {

        final User user = new User();
        user.setEmail("name");
        user.setPassword("123456");
        user.setRole(Role.USER);

        when(repository.findByEmail(any(String.class))).thenReturn(user);
        UserDetails loaded = service.loadUserByUsername("name");

        assertThat(loaded.getUsername(), Matchers.equalTo(user.getEmail()));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldFailToLoadByUsernameWhenUserNotExists() {
        service.loadUserByUsername("name");
    }
}
