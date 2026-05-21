package learn.watchlist.data;

import learn.watchlist.models.AdminStatus;
import learn.watchlist.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {

    @Autowired
    JdbcClient jdbcClient;

    @Autowired
    UserJdbcClientRepository repository;

    @BeforeEach
    void setup() { jdbcClient.sql("call set_known_good_state();").update(); }

    @Test
    void shouldAdd() {
        User user = new User();
        user.setUsername("user4");
        user.setPassword("password");
        User actual = repository.create(user);

        assertEquals(4, actual.getId());
    }

    @Test
    void shouldFind() {
        User actual = repository.findByUsername("user1");

        assertEquals("user1", actual.getUsername());
        assertEquals(AdminStatus.NOT_ADMIN, actual.getAdminStatus());
        assertEquals(2, actual.getStats().getMoviesWatched());
        assertEquals(185, actual.getStats().getMinutesWatched());
    }

    @Test
    void shouldFindNoStats() {
        User actual = repository.findByUsername("user3");

        assertEquals("user3", actual.getUsername());
        assertEquals(AdminStatus.NOT_ADMIN, actual.getAdminStatus());
        assertEquals(0, actual.getStats().getMoviesWatched());
        assertEquals(0, actual.getStats().getMinutesWatched());
    }
}