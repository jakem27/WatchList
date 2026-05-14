package learn.watchlist.models;

public class MovieFolder {
    private int folderId;
    private Movie movie;
    private boolean watched;
    private boolean liked;

    public MovieFolder() {}

    public MovieFolder(int folderId, Movie movie) {
        this.folderId = folderId;
        this.movie = movie;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
