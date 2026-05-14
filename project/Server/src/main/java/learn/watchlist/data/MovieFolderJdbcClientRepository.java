package learn.watchlist.data;

import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieFolderJdbcClientRepository implements MovieFolderRepository {

    private final JdbcClient jdbcClient;

    public MovieFolderJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Movie> findMoviesInFolder(int folderId) {
        return List.of();
    }

    @Override
    public boolean add(MovieFolder movieFolder) {
        final String sql = """
                insert into movie_folder(movie_id, folder_id, watched)
                values (:movie_id, :folder_id, :watched);
                """;

        return jdbcClient.sql(sql)
                .param("movie_id", movieFolder.getMovie().getId())
                .param("folder_id", movieFolder.getFolder().getId())
                .param("watched", movieFolder.isWatched())
                .update() > 0;
    }
}
