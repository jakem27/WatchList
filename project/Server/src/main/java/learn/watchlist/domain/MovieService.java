package learn.watchlist.domain;

import learn.watchlist.data.MovieRepository;
import learn.watchlist.data.OmdbClient;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Movie;
import learn.watchlist.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final OmdbClient omdbClient;

    private final UserRepository userRepository;

    public MovieService(MovieRepository movieRepository, OmdbClient omdbClient, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.omdbClient = omdbClient;
        this.userRepository = userRepository;
    }

    public Result<Movie> findByTitle(String title) {
        Result<Movie> result = new Result<>();
        if(title.isBlank()) {
            result.addMessage("Title is required", ResultType.INVALID);
            return result;
        }

        Movie movie = movieRepository.findByTitle(title);

        if(movie == null) {
            movie = omdbClient.fetchMovie(title);
            if(movie != null && movie.getTitle().equalsIgnoreCase(title)) {
                movie = movieRepository.add(movie);
                result.setPayload(movie);
            } else {
                result.addMessage("Movie not found", ResultType.NOT_FOUND);
            }
        } else {
            result.setPayload(movie);
        }

        return result;
    }

    public Result<Void> updateServices(String title, List<String> services, String username) {
        Result<Void> result = new Result<>();
        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        if(services == null) {
            result.addMessage("Services required", ResultType.INVALID);
            return result;
        }

        Movie movie = movieRepository.findByTitle(title);
        if(movie == null) {
            result.addMessage("Movie not found", ResultType.NOT_FOUND);
            return result;
        }

        boolean success = movieRepository.updateServices(movie.getId(), services);
        if(!success) {
            result.addMessage("Failed to update services", ResultType.INVALID);
        }

        return result;
    }
}
