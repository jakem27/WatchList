package learn.watchlist;

import learn.watchlist.models.Folder;
import learn.watchlist.models.User;

public class TestHelper {
    public static User makeUser() {
        User user = new User();
        user.setUsername("test user");
        user.setPassword("password");
        user.setFavoriteMovie("movie");
        user.setFavoriteActor("actor");
        user.setId(5);
        return user;
    }

    public static Folder makeFolder() {
        Folder folder = new Folder();
        folder.setName("test folder");
        folder.setPublic(false);
        folder.setParent_id(0);
        folder.setUser(makeUser());
        return folder;
    }
}
