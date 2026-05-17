package learn.watchlist.data;

import learn.watchlist.data.mappers.FriendshipMapper;
import learn.watchlist.models.Friendship;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendshipJdbcClientRepository implements FriendshipRepository{

    private final JdbcClient jdbcClient;

    public FriendshipJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Friendship findByUsers(int user1Id, int user2Id) {
        final String sql = """
                select u1.id, u1.username, u2.id, u2.username, f.pending
                from friendship f
                join user u1 on u1.id = f.user1_id
                join user u2 on u2.id = f.user2_id
                where (u1.id = :user1_id and u2.id = :user2_id)
                or (u1.id = :user2_id and u2.id = :user1_id);
                """;

        return jdbcClient.sql(sql)
                .param("user1_id", user1Id)
                .param("user2_id", user2Id)
                .query(new FriendshipMapper())
                .optional().orElse(null);
    }

    @Override
    public List<Friendship> findFriends(int userId) {
        final String sql = """
                select u1.id, u1.username, u2.id, u2.username, f.pending
                from friendship f
                join user u1 on u1.id = f.user1_id
                join user u2 on u2.id = f.user2_id
                where u1.id = :id or u2.id = :id;
                """;

        return jdbcClient.sql(sql)
                .param("id", userId)
                .query(new FriendshipMapper())
                .list();
    }

    @Override
    public boolean addRequest(Friendship friendship) {
        final String sql = """
                insert into friendship(user1_id, user2_id, pending)
                values(:user1_id, :user2_id, 1);
                """;

        return jdbcClient.sql(sql)
                .param("user1_id", friendship.getUser1().getId())
                .param("user2_id", friendship.getUser2().getId())
                .update() > 0;
    }
}
