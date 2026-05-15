package learn.watchlist.data;

import learn.watchlist.models.Movie;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MovieJdbcClientRepository  implements MovieRepository {
    private final JdbcClient jdbcClient;

    public MovieJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Movie findByTitle(String title) {
        final String sql = """
                select id, title, year, runtime, director, genre, poster_url
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
                insert into movie (title, year, runtime, director, genre, poster_url)
                values (:title, :year, :runtime, :director, :genre, :poster_url);
                """;

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("title", movie.getTitle())
                .param("year", movie.getYear())
                .param("runtime", movie.getRuntime())
                .param("director", movie.getDirector())
                .param("genre", movie.getGenre())
                .param("poster_url", movie.getPosterUrl())
                .update(keyHolder, "id");

        if(rowsAffected == 0) {
            return null;
        }

        movie.setId(keyHolder.getKey().intValue());
        return movie;
    }
}
