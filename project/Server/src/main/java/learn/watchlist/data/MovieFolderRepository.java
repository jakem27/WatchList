package learn.watchlist.data;

import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;

import java.util.List;

public interface MovieFolderRepository {
    List<Movie> findMoviesInFolder(int folderId);

    boolean add(MovieFolder movieFolder);
}
