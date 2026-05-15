package learn.watchlist.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OmdbMovieResponse {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Runtime")
    private String runtime;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Poster")
    private String posterUrl;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public String getResponse() {
        return response;
    }

    public String getPosterUrl() { return posterUrl; }
}
