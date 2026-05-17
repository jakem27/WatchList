package learn.watchlist.data;

import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FriendshipJdbcClientRepositoryTest {

    @Autowired
    JdbcClient jdbcClient;

    @Autowired
    FriendshipRepository repository;

    private final User user1;
    private final User user2;
    private final User user3;

    FriendshipJdbcClientRepositoryTest() {
        user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");

        user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");

        user3 = new User();
        user3.setId(3);
        user3.setUsername("user3");
    }

    @BeforeEach
    void setup() {
        jdbcClient.sql("call set_known_good_state();").update();
    }

    @Test
    void shouldAddRequest() {
        Friendship friendship = new Friendship(user1, user3);
        assertTrue(repository.addRequest(friendship));
    }

    @Test
    void shouldFindSingleFriendship() {
        Friendship expected = new Friendship(user1, user2);
        Friendship actual = repository.findByUsers(1, 2);

        assertEquals(expected, actual);

        actual = repository.findByUsers(2, 1);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindFriendships() {
        List<Friendship> expected = List.of(
                new Friendship(user1, user2),
                new Friendship(user2, user3)
        );

        List<Friendship> actual = repository.findFriends(2);

        assertEquals(expected, actual);
    }
}