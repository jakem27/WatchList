package learn.watchlist.domain;

import learn.watchlist.data.FriendshipRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Friendship;
import learn.watchlist.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public Result<List<User>> findFriends(String username) {
        Result<List<User>> result = new Result<>();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        result.setPayload(friendshipRepository.findFriends(user.getId()));
        return result;
    }

    public Result<List<User>> findRequests(String username) {
        Result<List<User>> result = new Result<>();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        result.setPayload(friendshipRepository.findRequests(user.getId()));
        return result;
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

        Friendship existing = friendshipRepository.findByUsers(user.getId(), friend.getId());
        if(existing != null) {
            if(!existing.isPending()) {
                result.addMessage("Already friends with " + friendUsername, ResultType.INVALID);
                return result;
            }
            if(existing.getUser1().getId() == user.getId()) {
                result.addMessage("Waiting for " + friendUsername + " to accept friendship request", ResultType.INVALID);
                return result;
            }
            result.addMessage(friendUsername + " is already waiting for you to accept their friendship request", ResultType.INVALID);
            return result;
        }

        boolean success = friendshipRepository.addRequest(user.getId(), friend.getId());
        if(!success) {
            result.addMessage("Failed to add request", ResultType.INVALID);
        }

        return result;
    }

    public Result<Void> acceptRequest(String username, String friendUsername) {
        Result<Void> result = new Result<>();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        User friend = userRepository.findByUsername(friendUsername);
        if(friend == null) {
            result.addMessage("Friend does not exist", ResultType.NOT_FOUND);
            return result;
        }

        if(user.getId() == friend.getId()) {
            result.addMessage("Cannot accept friend request from yourself", ResultType.INVALID);
            return result;
        }

        Friendship existing = friendshipRepository.findByUsers(user.getId(), friend.getId());
        if(existing != null) {
            if(!existing.isPending()) {
                result.addMessage("Already friends with " + friendUsername, ResultType.INVALID);
                return result;
            }
            if(existing.getUser1().getId() == user.getId()) {
                result.addMessage("Waiting for " + friendUsername + " to accept friendship request", ResultType.INVALID);
                return result;
            }
        } else {
            result.addMessage("No pending friend request to accept", ResultType.INVALID);
            return result;
        }

        boolean success = friendshipRepository.acceptRequest(friend.getId(), user.getId());
        if(!success) {
            result.addMessage("Failed to accept request", ResultType.INVALID);
        }

        return result;
    }

    public Result<Void> delete(String username, String friendUsername) {
        Result<Void> result = new Result<>();

        User user = userRepository.findByUsername(username);
        if(user == null) {
            result.addMessage("Invalid user", ResultType.INVALID);
            return result;
        }

        User friend = userRepository.findByUsername(friendUsername);
        if(friend == null) {
            result.addMessage("Friend does not exist", ResultType.NOT_FOUND);
            return result;
        }

        if(user.getId() == friend.getId()) {
            result.addMessage("Cannot delete friendship with yourself", ResultType.INVALID);
            return result;
        }

        Friendship existing = friendshipRepository.findByUsers(user.getId(), friend.getId());
        if(existing == null) {
            result.addMessage("No friendship to delete", ResultType.INVALID);
            return result;
        }

        boolean success = friendshipRepository.delete(friend.getId(), user.getId());
        if(!success) {
            result.addMessage("Failed to accept request", ResultType.INVALID);
        }

        return result;
    }


}
