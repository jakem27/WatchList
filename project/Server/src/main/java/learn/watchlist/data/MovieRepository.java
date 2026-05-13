package learn.watchlist.data;

import learn.watchlist.models.Movie;

public interface MovieRepository {
    Movie findByTitle(String title);

    Movie add(Movie movie);
}
