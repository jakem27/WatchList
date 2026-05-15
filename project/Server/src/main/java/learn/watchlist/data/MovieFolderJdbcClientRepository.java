package learn.watchlist.data;

import learn.watchlist.data.mappers.MovieFolderMapper;
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
    public List<MovieFolder> findByFolderId(int folderId) {
        final String sql = """
                WITH RECURSIVE folder_tree AS (
                    SELECT id
                    FROM folder
                    WHERE id = ?
                    
                    UNION ALL
                    
                    SELECT f.id
                    FROM FOLDER f
                    JOIN folder_tree ft ON f.parent_id = ft.id
                )
                
                SELECT m.id, m.title, m.year, m.runtime, m.director, m.genre,
                f.id, f.name, mf.watched, mf.liked
                FROM movie_folder mf
                JOIN movie m ON m.id = mf.movie_id
                JOIN folder f ON f.id = mf.folder_id
                WHERE f.id IN (SELECT id FROM folder_tree);
                """;

        return jdbcClient.sql(sql)
                .param(folderId)
                .query(new MovieFolderMapper())
                .list();
    }

    @Override
    public boolean add(MovieFolder movieFolder) {
        final String sql = """
                insert into movie_folder(movie_id, folder_id, watched, liked)
                values(:movie_id, :folder_id, 0, 0);
                """;

        return jdbcClient.sql(sql)
                .param("movie_id", movieFolder.getMovie().getId())
                .param("folder_id", movieFolder.getFolder().getId())
                .update() > 0;
    }
}
