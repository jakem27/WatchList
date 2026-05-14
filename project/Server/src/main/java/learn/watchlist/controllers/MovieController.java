package learn.watchlist.controllers;

import learn.watchlist.domain.MovieService;
import learn.watchlist.domain.Result;
import learn.watchlist.domain.ResultType;
import learn.watchlist.models.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> findMovie(@PathVariable("title") String title) {
        Result<Movie> result = service.findByTitle(title);
        if(result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        } else if(result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }
}
