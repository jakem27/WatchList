package learn.watchlist.data;

import learn.watchlist.TestHelper;
import learn.watchlist.models.Folder;
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
        Folder folder = new Folder("f1", false, TestHelper.makeUser(), 1);
        folder.setId(2);
        MovieFolder movieFolder = new MovieFolder(movie, folder);

        assertTrue(repository.add(movieFolder));
    }

}