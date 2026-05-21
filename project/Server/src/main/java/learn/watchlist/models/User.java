package learn.watchlist.models;

import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String password;
    private String favoriteMovie;
    private String favoriteActor;
    private String favoriteGenre;
    private AdminStatus adminStatus;
    private Stats stats;

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

    public String getFavoriteMovie() {
        return favoriteMovie;
    }

    public void setFavoriteMovie(String favoriteMovie) {
        this.favoriteMovie = favoriteMovie;
    }

    public String getFavoriteActor() {
        return favoriteActor;
    }

    public void setFavoriteActor(String favoriteActor) {
        this.favoriteActor = favoriteActor;
    }

    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public AdminStatus getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(favoriteMovie, user.favoriteMovie) && Objects.equals(favoriteActor, user.favoriteActor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, favoriteMovie, favoriteActor);
    }
}
