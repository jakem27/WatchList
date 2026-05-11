package learn.watchlist.data;

import learn.watchlist.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository {

    private final JdbcClient jdbcClient;

    public UserJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User findByUsername(String username) {
        final String sql = """
                select username, password, favorite_movie, favorite_actor
                from user
                where username = ?;
                """;

        return jdbcClient.sql(sql)
                .param(username)
                .query(User.class)
                .optional().orElse(null);
    }

    @Override
    public User create(User user) {
        final String sql = """
                insert into user(username, password)
                values (:username, :password);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("username", user.getUsername())
                .param("password", user.getPassword())
                .update(keyHolder, "id");

        if(rowsAffected == 0) {
            return null;
        }

        user.setId(keyHolder.getKey().intValue());
        return user;
    }
}
