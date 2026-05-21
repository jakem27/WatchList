package learn.watchlist.data.mappers;

import learn.watchlist.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setFavoriteActor(rs.getString("favorite_actor"));
        user.setFavoriteMovie(rs.getString("favorite_movie"));
        user.setFavoriteGenre(rs.getString("favorite_genre"));

        return user;
    }
}
