package learn.watchlist.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String favorite_movie;
    private String favorite_actor;

    public User() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFavorite_movie() {
        return favorite_movie;
    }

    public void setFavorite_movie(String favorite_movie) {
        this.favorite_movie = favorite_movie;
    }

    public String getFavorite_actor() {
        return favorite_actor;
    }

    public void setFavorite_actor(String favorite_actor) {
        this.favorite_actor = favorite_actor;
    }
}
