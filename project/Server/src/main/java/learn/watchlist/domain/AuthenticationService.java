package learn.watchlist.domain;

import learn.watchlist.data.UserRepository;
import learn.watchlist.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public AuthenticationService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public Result<User> authenticate(User proposedUser) {
        Result<User> result = new Result<>();

        User userFromDb = repository.findByUsername(proposedUser.getUsername());
        if(userFromDb == null) {
            result.addMessage("User does not exist", ResultType.NOT_FOUND);
            return result;
        }

        if(encoder.matches(proposedUser.getPassword(), userFromDb.getPassword())) {
            result.setPayload(userFromDb);
        } else {
            result.addMessage("Incorrect password", ResultType.INVALID);
        }

        return result;
    }

    public Result<User> create(User user) {
        Result<User> result = new Result<>();

        if(user == null) {
            result.addMessage("User is required", ResultType.INVALID);
            return result;
        }

        if(user.getUsername().isBlank()) {
            result.addMessage("Username is required", ResultType.INVALID);
        }

        if(user.getPassword().isBlank()) {
            result.addMessage("Password is required", ResultType.INVALID);
        }

        if(user.getId() != 0) {
            result.addMessage("Id cannot be set for `create` operation", ResultType.INVALID);
        }

        if(repository.findByUsername(user.getUsername()) != null) {
            result.addMessage("That username is taken", ResultType.INVALID);
        }

        if(result.isSuccess()) {
            user.setPassword(encoder.encode(user.getPassword()));

            user = repository.create(user);
            result.setPayload(user);
        }

        return result;
    }

}
