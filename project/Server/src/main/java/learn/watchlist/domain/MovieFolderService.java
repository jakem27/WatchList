package learn.watchlist.domain;

import learn.watchlist.data.FolderRepository;
import learn.watchlist.data.MovieFolderRepository;
import learn.watchlist.data.MovieRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Folder;
import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;
import learn.watchlist.models.User;
import org.springframework.stereotype.Service;

@Service
public class MovieFolderService {
    private final MovieFolderRepository movieFolderRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final MovieRepository movieRepository;

    public MovieFolderService(MovieFolderRepository movieFolderRepository, UserRepository userRepository, FolderRepository folderRepository, MovieRepository movieRepository) {
        this.movieFolderRepository = movieFolderRepository;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.movieRepository = movieRepository;
    }

    public Result<Void> add(MovieFolder movieFolder, String username) {
        Result<Void> result = new Result<>();

        if(movieFolder == null) {
            result.addMessage("MovieFolder is required", ResultType.INVALID);
            return result;
        }

        if(movieFolder.getFolder() == null) {
            result.addMessage("Folder is required", ResultType.INVALID);
            return result;
        }

        if(movieFolder.getMovie() == null) {
            result.addMessage("Movie is required", ResultType.INVALID);
            return result;
        }

        authenticate(result, username);
        if(!result.isSuccess()) {
            return result;
        }

        Folder folder = folderRepository.findById(movieFolder.getFolder().getId());
        if(folder == null) {
            result.addMessage("Folder does not exist", ResultType.NOT_FOUND);
            return result;
        }
        if(!username.equals(folder.getUser().getUsername())) {
            result.addMessage("Cannot add movie to someone else's folder", ResultType.INVALID);
        }

        Movie movie = movieRepository.findByTitle(movieFolder.getMovie().getTitle());
        if(movie == null) {
            result.addMessage("Movie not found", ResultType.NOT_FOUND);
            return result;
        }

        boolean success = movieFolderRepository.add(movieFolder);
        if(!success) {
            result.addMessage("Failed to add", ResultType.INVALID);
        }
        return result;
    }

    private User authenticate(Result<?> result, String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
        }
        return user;
    }
}
