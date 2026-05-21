package learn.watchlist.data;

import learn.watchlist.models.User;

import java.util.List;

public interface UserRepository {
    User findByUsername(String username);

    User create(User user);

    boolean update(User user);

    boolean updateServices(int userId, List<String> services);
}
