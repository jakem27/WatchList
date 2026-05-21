package learn.watchlist.data;

import learn.watchlist.data.mappers.UserMapper;
import learn.watchlist.models.Stats;
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
                select id, username, password, favorite_movie, favorite_actor, favorite_genre, admin_status
                from user
                where username = ?;
                """;

        User user = jdbcClient.sql(sql)
                .param(username)
                .query(User.class)
                .optional().orElse(null);

        if(user != null) {
            addStats(user);
        }

        return user;
    }

    @Override
    public User create(User user) {
        final String sql = """
                insert into user(username, password, admin_status)
                values (:username, :password, "NOT_ADMIN");
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

    public boolean update(User user) {
        final String sql = """
                update user
                set favorite_movie = :movie, favorite_actor = :actor, favorite_genre = :genre
                where id = :id;
                """;
        return jdbcClient.sql(sql)
                .param("movie", user.getFavoriteMovie())
                .param("actor", user.getFavoriteActor())
                .param("genre", user.getFavoriteGenre())
                .param("id", user.getId())
                .update() > 0;
    }

    private void addStats(User user) {
        final String sql = """
                select count(*) as movies_watched, coalesce(sum(m.runtime), 0) as minutes_watched
                from user u
                join folder f on u.id = f.user_id
                join movie_folder mf on f.id = mf.folder_id
                join movie m on m.id = mf.movie_id
                where u.id = ? and mf.watched = 1;
                """;

        Stats stats = jdbcClient.sql(sql)
                .param(user.getId())
                .query(Stats.class)
                .optional().orElse(null);


        user.setStats(stats);
    }
}
