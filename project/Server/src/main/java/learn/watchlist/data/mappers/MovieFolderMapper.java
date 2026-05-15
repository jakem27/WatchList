package learn.watchlist.data.mappers;

import learn.watchlist.models.Folder;
import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieFolderMapper implements RowMapper<MovieFolder> {

    @Override
    public MovieFolder mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("m.id"));
        movie.setTitle(rs.getString("m.title"));
        movie.setYear(rs.getInt("m.year"));
        movie.setRuntime(rs.getInt("m.runtime"));
        movie.setDirector(rs.getString("m.director"));
        movie.setGenre(rs.getString("m.genre"));

        Folder folder = new Folder();
        folder.setId(rs.getInt("f.id"));
        folder.setName(rs.getString("f.name"));

        MovieFolder movieFolder = new MovieFolder();
        movieFolder.setMovie(movie);
        movieFolder.setFolder(folder);
        movieFolder.setWatched(rs.getBoolean("mf.watched"));
        movieFolder.setLiked(rs.getBoolean("mf.liked"));

        return movieFolder;
    }
}
