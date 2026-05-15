package learn.watchlist.models;

public class MovieFolder {
    private Movie movie;
    private Folder folder;
    private boolean watched;
    private boolean liked;

    public MovieFolder() {}

    public MovieFolder(Movie movie, Folder folder) {
        this.folder = folder;
        this.movie = movie;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
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
