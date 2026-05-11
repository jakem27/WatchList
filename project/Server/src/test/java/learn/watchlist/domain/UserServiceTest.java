package learn.watchlist.domain;

import learn.watchlist.data.UserRepository;
import learn.watchlist.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockitoBean
    UserRepository repository;

    @Test
    void shouldAdd() {
        User user = makeUser();
        User mockOut = makeUser();
        mockOut.setId(1);

        when(repository.findByUsername(user.getUsername())).thenReturn(null);
        when(repository.create(user)).thenReturn(mockOut);

        Result<User> result = service.create(user);
        assertTrue(result.isSuccess());

    }

    @Test
    void shouldNotAddNullUser() {
        User user = null;

        Result<User> result = service.create(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("User is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddBlankUsername() {
        User user = makeUser();
        user.setUsername("");

        when(repository.findByUsername(user.getUsername())).thenReturn(null);

        Result<User> result = service.create(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Username is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddBlankPassword() {
        User user = makeUser();
        user.setPassword("");

        when(repository.findByUsername(user.getUsername())).thenReturn(null);

        Result<User> result = service.create(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Password is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddUsernameTaken() {
        User user = makeUser();

        when(repository.findByUsername(user.getUsername())).thenReturn(makeUser());

        Result<User> result = service.create(user);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("That username is taken", result.getMessages().get(0));
    }

    @Test
    void shouldLogin() {
        User proposed = makeUser();

        when(repository.findByUsername(proposed.getUsername())).thenReturn(makeUser());

        Result<User> result = service.authenticate(proposed);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotLoginDoesNotExist() {
        User proposed = makeUser();

        when(repository.findByUsername(proposed.getUsername())).thenReturn(null);

        Result<User> result = service.authenticate(proposed);
        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("User does not exist", result.getMessages().get(0));
    }

    @Test
    void shouldNotLoginIncorrectPassword() {
        User proposed = makeUser();
        User existing = makeUser();
        existing.setPassword("different password");

        when(repository.findByUsername(proposed.getUsername())).thenReturn(existing);

        Result<User> result = service.authenticate(proposed);
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Incorrect password", result.getMessages().get(0));
    }

    private User makeUser() {
        User user = new User();
        user.setUsername("user123");
        user.setPassword("123");
        return user;
    }
}