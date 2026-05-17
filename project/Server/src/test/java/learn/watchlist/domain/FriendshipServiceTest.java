package learn.watchlist.domain;

import learn.watchlist.TestHelper;
import learn.watchlist.data.FriendshipRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FriendshipServiceTest {

    @Autowired
    FriendshipService service;

    @MockitoBean
    FriendshipRepository friendshipRepository;

    @MockitoBean
    UserRepository userRepository;

    @Test
    void shouldAddRequest() {
        User user1 = TestHelper.makeUser();
        User user2 = TestHelper.makeUser2();
        Friendship friendship = new Friendship(user1, user2);

        when(userRepository.findByUsername("test user")).thenReturn(user1);
        when(userRepository.findByUsername("friend")).thenReturn(user2);
        when(friendshipRepository.findByUsers(user1.getId(), user2.getId())).thenReturn(null);
        when(friendshipRepository.addRequest(user1.getId(), user2.getId())).thenReturn(true);

        Result<Void> result = service.addRequest("test user", "friend");
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddUserDoesNotExist() {
        when(userRepository.findByUsername("fake")).thenReturn(null);

        Result<Void> result = service.addRequest("fake", "friend");

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Invalid user", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddFriendDoesNotExist() {
        when(userRepository.findByUsername("user")).thenReturn(new User());
        when(userRepository.findByUsername("fake")).thenReturn(null);

        Result<Void> result = service.addRequest("user", "fake");

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Requested friend does not exist", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddRequestedSelf() {
        when(userRepository.findByUsername("test user")).thenReturn(TestHelper.makeUser());

        Result<Void> result = service.addRequest("test user", "test user");

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Cannot send friend request to yourself", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddFriendshipAlreadyExists() {
        User user1 = TestHelper.makeUser();
        User user2 = TestHelper.makeUser2();

        Friendship friendship = new Friendship(user1, user2);
        friendship.setPending(false);

        when(userRepository.findByUsername("test user")).thenReturn(user1);
        when(userRepository.findByUsername("friend")).thenReturn(user2);
        when(friendshipRepository.findByUsers(user1.getId(), user2.getId())).thenReturn(friendship);

        Result<Void> result = service.addRequest("test user", "friend");
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Already friends with friend", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddPendingFriend() {
        User user1 = TestHelper.makeUser();
        User user2 = TestHelper.makeUser2();

        Friendship friendship = new Friendship(user1, user2);

        when(userRepository.findByUsername("test user")).thenReturn(user1);
        when(userRepository.findByUsername("friend")).thenReturn(user2);
        when(friendshipRepository.findByUsers(user1.getId(), user2.getId())).thenReturn(friendship);

        Result<Void> result = service.addRequest("test user", "friend");
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Waiting for friend to accept friendship request", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddPendingUser() {
        User user1 = TestHelper.makeUser();
        User user2 = TestHelper.makeUser2();

        Friendship friendship = new Friendship(user2, user1);

        when(userRepository.findByUsername("test user")).thenReturn(user1);
        when(userRepository.findByUsername("friend")).thenReturn(user2);
        when(friendshipRepository.findByUsers(user1.getId(), user2.getId())).thenReturn(friendship);

        Result<Void> result = service.addRequest("test user", "friend");
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("friend is already waiting for you to accept their friendship request", result.getMessages().get(0));
    }

}