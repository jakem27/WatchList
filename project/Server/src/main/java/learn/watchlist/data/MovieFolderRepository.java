package learn.watchlist.data;

import learn.watchlist.models.MovieFolder;

import java.util.List;

public interface MovieFolderRepository {
    List<MovieFolder> findByFolderId(int folderId);

    MovieFolder findByUserIdMovieId(int userId, int movieId);

    boolean add(MovieFolder movieFolder);

    boolean update(MovieFolder movieFolder);
}
