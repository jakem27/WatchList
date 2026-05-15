package learn.watchlist.data;

import learn.watchlist.TestHelper;
import learn.watchlist.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MovieJdbcClientRepositoryTest {

    @Autowired
    JdbcClient jdbcClient;

    @Autowired
    MovieJdbcClientRepository repository;

    @BeforeEach
    void setup() { jdbcClient.sql("call set_known_good_state();").update(); }

    @Test
    void shouldAdd() {
        Movie movie = TestHelper.makeMovie();

        Movie actual = repository.add(movie);
        assertEquals(7, actual.getId());
    }

    @Test
    void shouldFindMovie() {
        Movie actual = repository.findByTitle("movie2");
        assertEquals(2, actual.getId());
        assertEquals(2002, actual.getYear());
    }
}