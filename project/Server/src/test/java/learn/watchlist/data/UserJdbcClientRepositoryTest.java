package learn.watchlist.data;

import learn.watchlist.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {

    @Autowired
    UserJdbcClientRepository repository;

    @Test
    void shouldAdd() {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("password");
        User actual = repository.create(user);

        assertEquals(1, actual.getId());
    }
}