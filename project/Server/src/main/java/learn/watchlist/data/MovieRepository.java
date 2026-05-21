package learn.watchlist.data;

import learn.watchlist.models.Movie;

import java.util.List;

public interface MovieRepository {
    Movie findByTitle(String title);

    Movie add(Movie movie);

    boolean updateServices(int movieId, List<String> services);
}
