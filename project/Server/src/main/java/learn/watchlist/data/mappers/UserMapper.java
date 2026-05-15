package learn.watchlist.data.mappers;

import learn.watchlist.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("u.id"));
        user.setPassword(rs.getString("u.password"));
        user.setUsername(rs.getString("u.username"));
        user.setFavoriteActor(rs.getString("u.favorite_actor"));
        user.setFavoriteMovie(rs.getString("u.favorite_movie"));

        return user;
    }
}
