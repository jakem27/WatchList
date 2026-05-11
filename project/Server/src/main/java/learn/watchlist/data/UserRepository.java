package learn.watchlist.data;

import learn.watchlist.models.User;

public interface UserRepository {
    User findByUsername(String username);

    User create(User user);
}
