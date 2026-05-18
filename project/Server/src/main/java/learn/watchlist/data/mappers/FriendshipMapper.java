package learn.watchlist.data.mappers;

import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipMapper implements RowMapper<Friendship> {

    @Override
    public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user1 = new User();
        user1.setId(rs.getInt("u1.id"));
        user1.setUsername(rs.getString("u1.username"));

        User user2 = new User();
        user2.setId(rs.getInt("u2.id"));
        user2.setUsername(rs.getString("u2.username"));

        Friendship friendship = new Friendship();
        friendship.setPending(rs.getBoolean("pending"));
        friendship.setUser1(user1);
        friendship.setUser2(user2);

        return friendship;
    }
}
