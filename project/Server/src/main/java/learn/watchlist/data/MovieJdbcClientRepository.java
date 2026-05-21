package learn.watchlist.data;

import learn.watchlist.models.Movie;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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

        Movie movie = jdbcClient.sql(sql)
                .param(title)
                .query(Movie.class)
                .optional().orElse(null);

        if(movie != null) {
            addStreamingServices(movie);
        }

        return movie;
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

    private void addStreamingServices(Movie movie) {
        final String sql = """
                select ms.streaming_service
                from movie m
                join movie_service ms on m.id = ms.movie_id
                where m.id = ?;
                """;

        List<String> services = jdbcClient.sql(sql)
                .param(movie.getId())
                .query(String.class)
                .list();

        movie.setServices(services);
    }
}
