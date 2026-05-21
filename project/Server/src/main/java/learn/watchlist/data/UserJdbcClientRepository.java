package learn.watchlist.data;

import learn.watchlist.data.mappers.UserMapper;
import learn.watchlist.models.Stats;
import learn.watchlist.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcClientRepository implements UserRepository {

    private final JdbcClient jdbcClient;
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcClientRepository(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
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
            addStreamingServices(user);
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

    public boolean updateServices(int userId, List<String> services) {
        final String deleteSql = """
                delete from user_service
                where user_id = ?;
                """;

        jdbcClient.sql(deleteSql)
                .param(userId)
                .update();

        if(services.isEmpty()) return true;

        final String addSql = """
                insert into user_service (user_id, streaming_service)
                values (?, ?);
                """;

        jdbcTemplate.batchUpdate(
                addSql,
                services,
                services.size(),
                (ps, service) -> {
                    ps.setInt(1, userId);
                    ps.setString(2, service);
                }
        );

        return true;
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

    private void addStreamingServices(User user) {
        final String sql = """
                select us.streaming_service
                from user u
                join user_service us on u.id = us.user_id
                where u.id = ?;
                """;

        List<String> services = jdbcClient.sql(sql)
                .param(user.getId())
                .query(String.class)
                .list();

        user.setServices(services);
    }
}
