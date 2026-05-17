package learn.watchlist.data;

import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

public interface FriendshipRepository {
    Friendship findByUsers(int user1Id, int user2Id);

    List<Friendship> findFriends(int userId);

    boolean addRequest(Friendship friendship);
}
