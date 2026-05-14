package learn.watchlist.data;

import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MovieFolderJdbcClientRepositoryTest {
    @Autowired
    JdbcClient jdbcClient;

    @Autowired
    MovieFolderRepository repository;

    @BeforeEach
    void setup() { jdbcClient.sql("call set_known_good_state();").update(); }

    @Test
    void shouldAdd() {
        Movie movie = new Movie(1, "movie1", 2001, 90, "director1", "action");
        MovieFolder movieFolder = new MovieFolder(4, movie);

        assertTrue(repository.add(movieFolder));
    }

}