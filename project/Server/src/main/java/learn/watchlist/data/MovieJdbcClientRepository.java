package learn.watchlist.data;

import learn.watchlist.models.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieJdbcClientRepository  implements MovieRepository {
    private final JdbcClient jdbcClient;
    private final JdbcTemplate jdbcTemplate;

    public MovieJdbcClientRepository(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie findByTitle(String title) {
        final String sql = """
                select id, title, year, runtime, director, genre, poster_url, description
                from movie
                where title = ?;
                """;

        return jdbcClient.sql(sql)
                .param(title)
                .query(Movie.class)
                .optional().orElse(null);
    }

    @Override
    public Movie add(Movie movie) {
        final String sql = """
                insert into movie (title, year, runtime, director, genre, description, poster_url)
                values (:title, :year, :runtime, :director, :genre, :description, :poster_url);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("title", movie.getTitle())
                .param("year", movie.getYear())
                .param("runtime", movie.getRuntime())
                .param("director", movie.getDirector())
                .param("genre", movie.getGenre())
                .param("poster_url", movie.getPosterUrl())
                .param("description", movie.getDescription())
                .update(keyHolder, "id");

        if(rowsAffected == 0) {
            return null;
        }

        movie.setId(keyHolder.getKey().intValue());
        movie.setServices(new ArrayList<>());
        return movie;
    }

    @Override
    public boolean updateServices(int movieId, List<String> services) {
        final String deleteSql = """
                delete from movie_service
                where movie_id = ?;
                """;

        jdbcClient.sql(deleteSql)
                .param(movieId)
                .update();

        if(services.isEmpty()) return true;

        final String addSql = """
                insert into movie_service (movie_id, streaming_service)
                values (?, ?);
                """;

        jdbcTemplate.batchUpdate(
                addSql,
                services,
                services.size(),
                (ps, service) -> {
                    ps.setInt(1, movieId);
                    ps.setString(2, service);
                }
        );

        return true;
    }

    public List<String> findServices(int movieId) {
        final String sql = """
                select ms.streaming_service
                from movie m
                join movie_service ms on m.id = ms.movie_id
                where m.id = ?;
                """;

        return jdbcClient.sql(sql)
                .param(movieId)
                .query(String.class)
                .list();
    }
}
