package learn.watchlist.data;

import learn.watchlist.models.Friendship;
import org.springframework.jdbc.core.simple.JdbcClient;

public interface FriendshipRepository {
    Friendship findByUsers(int user1Id, int user2Id);

    boolean addRequest(Friendship friendship);
}
