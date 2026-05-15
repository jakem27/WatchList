package learn.watchlist.domain;

import learn.watchlist.data.MovieRepository;
import learn.watchlist.data.OmdbClient;
import learn.watchlist.models.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository repository;
    private final OmdbClient omdbClient;

    public MovieService(MovieRepository repository, OmdbClient omdbClient) {
        this.repository = repository;
        this.omdbClient = omdbClient;
    }

    public Result<Movie> findByTitle(String title) {
        Result<Movie> result = new Result<>();
        if(title.isBlank()) {
            result.addMessage("Title is required", ResultType.INVALID);
            return result;
        }

        Movie movie = repository.findByTitle(title);

        if(movie == null) {
            movie = omdbClient.fetchMovie(title);
            if(movie != null && movie.getTitle().equalsIgnoreCase(title)) {
                movie = repository.add(movie);
                result.setPayload(movie);
            } else {
                result.addMessage("Movie not found", ResultType.NOT_FOUND);
            }
        } else {
            result.setPayload(movie);
        }

        return result;
    }
}
