package learn.watchlist.domain;

import learn.watchlist.data.FriendshipRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public Result<Void> addRequest(String username, String friendUsername) {
        Result<Void> result = new Result<>();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        User friend = userRepository.findByUsername(friendUsername);
        if(friend == null) {
            result.addMessage("Requested friend does not exist", ResultType.NOT_FOUND);
            return result;
        }

        if(user.getId() == friend.getId()) {
            result.addMessage("Cannot send friend request to yourself", ResultType.INVALID);
            return result;
        }

        boolean success = friendshipRepository.addRequest(new Friendship(user.getId(), friend.getId()));
        if(!success) {
            result.addMessage("Failed to add request", ResultType.INVALID);
        }

        return result;

    }
}
