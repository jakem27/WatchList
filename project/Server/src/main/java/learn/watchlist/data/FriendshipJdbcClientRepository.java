package learn.watchlist.data;

import learn.watchlist.models.Friendship;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class FriendshipJdbcClientRepository implements FriendshipRepository{

    private final JdbcClient jdbcClient;

    public FriendshipJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Friendship findByUsers(int user1Id, int user2Id) {
        return null;
    }

    @Override
    public boolean addRequest(Friendship friendship) {
        final String sql = """
                insert into friendship(user1_id, user2_id, pending)
                values(:user1_id, :user2_id, 1);
                """;

        return jdbcClient.sql(sql)
                .param("user1_id", friendship.getUser1Id())
                .param("user2_id", friendship.getUser2Id())
                .update() > 0;
    }
}
