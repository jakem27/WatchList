package learn.watchlist.data;

import learn.watchlist.models.User;

public interface UserRepository {
    User create(User user);
}
