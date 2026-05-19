package learn.watchlist.controllers;

import learn.watchlist.domain.MovieFolderService;
import learn.watchlist.domain.MovieService;
import learn.watchlist.domain.Result;
import learn.watchlist.domain.ResultType;
import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;
    private final MovieFolderService movieFolderService;

    public MovieController(MovieService movieService, MovieFolderService movieFolderService) {
        this.movieService = movieService;
        this.movieFolderService = movieFolderService;
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> findMovie(@PathVariable("title") String title) {
        Result<Movie> result = movieService.findByTitle(title);
        if(result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        } else if(result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping("/folder/{id}")
    public ResponseEntity<?> findMoviesInFolder(@PathVariable("id") int id, Authentication auth) {
        String username = auth.getName();
        Result<List<MovieFolder>> result = movieFolderService.findByFolderId(id, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody MovieFolder movieFolder, Authentication auth) {
        String username = auth.getName();
        Result<Void> result = movieFolderService.add(movieFolder, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateMovieFolder(@RequestBody MovieFolder movieFolder, Authentication auth) {
        String username = auth.getName();
        Result<Void> result = movieFolderService.update(movieFolder, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
