package learn.watchlist.data;

import learn.watchlist.models.Folder;
import learn.watchlist.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

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
        folder.setName("test");
        folder.setPublic(true);
        folder.setParent_id(0);
        folder.setUser(user);

        Folder actual = repository.add(folder);
        assertEquals(5, actual.getId());
    }

    @Test
    void shouldFindRoot() {
        List<Folder> actual = repository.findRoot("user1");

        assertEquals(2, actual.size());
        assertEquals("f1", actual.get(0).getName());
        assertEquals("f2", actual.get(1).getName());
    }

    @Test
    void shouldFindChildren() {
        List<Folder> actual = repository.findChildren(1);

        assertEquals(2, actual.size());
        assertEquals("f1", actual.get(0).getName());
        assertEquals("f2", actual.get(1).getName());

        actual = repository.findChildren(2);
        assertEquals(0, actual.size());
    }

}