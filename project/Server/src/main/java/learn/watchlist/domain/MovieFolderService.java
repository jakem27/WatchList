package learn.watchlist.domain;

import learn.watchlist.data.*;
import learn.watchlist.models.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieFolderService {
    private final MovieFolderRepository movieFolderRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final MovieRepository movieRepository;
    private final FriendshipRepository friendshipRepository;

    public MovieFolderService(MovieFolderRepository movieFolderRepository, UserRepository userRepository, FolderRepository folderRepository, MovieRepository movieRepository, FriendshipRepository friendshipRepository) {
        this.movieFolderRepository = movieFolderRepository;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.movieRepository = movieRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public Result<List<MovieFolder>> findByFolderId(int folderId, String username) {
        Result<List<MovieFolder>> result = new Result<>();
        User user = authenticateUser(result, username);

        Folder folder = folderRepository.findById(folderId);
        if(folder == null) {
            result.addMessage("Folder does not exist", ResultType.NOT_FOUND);
            return result;
        }

        if(!username.equals(folder.getUser().getUsername())) {
            // check for friendship and public/private
            if(!folder.isPublic()) {
                result.addMessage("Folder is private", ResultType.INVALID);
                return result;
            }

            Friendship friendship = friendshipRepository.findByUsers(folder.getUser().getId(), user.getId());
            if(friendship == null || friendship.isPending()) {
                result.addMessage("Cannot access someone else's folder if not friends", ResultType.INVALID);
                return result;
            }
        }

        List<MovieFolder> movieFolders = movieFolderRepository.findByFolderId(folderId);
        result.setPayload(movieFolders);
        return result;
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

        authenticateUser(result, username);
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
            return result;
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

    private User authenticateUser(Result<?> result, String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
        }

        return user;
    }

}
