package learn.watchlist.data;

import learn.watchlist.data.mappers.FriendshipMapper;
import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
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
    public List<User> findFriends(int userId) {
        final String sql = """
                select
                case
                    when u1.id = :id then u2.id
                    else u1.id
                end as id,
                case
                    when u1.id = :id then u2.username
                    else u1.username
                end as username
                from friendship f
                join user u1 on u1.id = f.user1_id
                join user u2 on u2.id = f.user2_id
                where pending = 0
                and (u1.id = :id or u2.id = :id);
                """;

        return jdbcClient.sql(sql)
                .param("id", userId)
                .query(User.class)
                .list();
    }

    @Override
    public List<User> findRequests(int userId) {
        final String sql = """
                select u1.id, u1.username
                from friendship f
                join user u1 on u1.id = f.user1_id
                join user u2 on u2.id = f.user2_id
                where pending = 1
                and u2.id = ?;
                """;

        return jdbcClient.sql(sql)
                .param(userId)
                .query(User.class)
                .list();
    }

    @Override
    public boolean addRequest(int user1Id, int user2Id) {
        final String sql = """
                insert into friendship(user1_id, user2_id, pending)
                values(:user1_id, :user2_id, 1);
                """;

        return jdbcClient.sql(sql)
                .param("user1_id", user1Id)
                .param("user2_id", user2Id)
                .update() > 0;
    }
}
