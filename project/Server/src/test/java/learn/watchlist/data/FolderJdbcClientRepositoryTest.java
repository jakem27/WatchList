package learn.watchlist.data;

import learn.watchlist.models.Folder;
import learn.watchlist.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FolderJdbcClientRepositoryTest {

    @Autowired
    JdbcClient jdbcClient;

    @Autowired
    FolderJdbcClientRepository repository;

    @BeforeEach
    void setup() { jdbcClient.sql("call set_known_good_state();").update(); }

    @Test
    void shouldAdd() {
        User user = new User();
        user.setId(1);
        Folder folder = new Folder();
        folder.setName("f2");
        folder.setPublic(true);
        folder.setParent_id(0);
        folder.setUser(user);

        Folder actual = repository.add(folder);
        assertEquals(3, actual.getId());
    }

}