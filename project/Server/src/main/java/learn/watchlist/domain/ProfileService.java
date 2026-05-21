package learn.watchlist.domain;

import learn.watchlist.data.FriendshipRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;


    public ProfileService(UserRepository userRepository, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public Result<User> findUser(String username) {
        Result<User> result = new Result<>();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        user.setPassword("");
        result.setPayload(user);
        return result;
    }

    public Result<User> findUser(String authUsername, String findUsername) {
        Result<User> result = new Result<>();

        User authUser = userRepository.findByUsername(authUsername);
        if(authUser == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        User user = userRepository.findByUsername(findUsername);
        if(user == null) {
            result.addMessage("User does not exist", ResultType.NOT_FOUND);
            return result;
        }

        Friendship friendship = friendshipRepository.findByUsers(authUser.getId(), user.getId());
        if(friendship == null || friendship.isPending()) {
            result.addMessage("Cannot view profile if not friends", ResultType.INVALID);
            return result;
        }

        user.setPassword("");
        result.setPayload(user);
        return result;
    }

    public Result<Void> updateProfile(User user, String username) {
        Result<Void> result = new Result<>();

        User authUser = userRepository.findByUsername(username);
        if(authUser == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        if(user == null) {
            result.addMessage("User required", ResultType.INVALID);
            return result;
        }

        if(user.getId() != authUser.getId() || !user.getUsername().equals(authUser.getUsername())) {
            result.addMessage("Cannot edit someone else's profile", ResultType.INVALID);
            return result;
        }

        boolean success = userRepository.update(user);
        if(!success) {
            result.addMessage("Failed to update", ResultType.INVALID);
        }

        return result;
    }

    public Result<Void> updateServices(List<String> services, String username) {
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

        boolean success = userRepository.updateServices(user.getId(), services);
        if(!success) {
            result.addMessage("Failed to update services", ResultType.INVALID);
        }

        return result;
    }

}
