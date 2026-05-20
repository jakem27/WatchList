package learn.watchlist.data;

import learn.watchlist.models.Movie;
import learn.watchlist.models.OmdbMovieResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OmdbClient {

    private final RestClient restClient;
    private final String apiKey;

    public OmdbClient(RestClient.Builder builder, @Value("${omdb.api.key}") String apiKey) {
        this.restClient = builder
                .baseUrl("https://www.omdbapi.com")
                .build();

        this.apiKey = apiKey;
    }

    public Movie fetchMovie(String title) {
        OmdbMovieResponse response = restClient.get()
                .uri("/?apikey=" + apiKey + "&t=" + title)
                .retrieve()
                .body(OmdbMovieResponse.class);

        if(response == null || response.getResponse().equals("False")) {
            return null;
        }

        return mapToMovie(response);
    }

    private Movie mapToMovie(OmdbMovieResponse response) {
        Movie movie = new Movie();

        movie.setTitle(response.getTitle());
        movie.setYear(Integer.parseInt(response.getYear()));
        movie.setRuntime(Integer.parseInt(response.getRuntime().replace(" min", "")));
        movie.setDirector(response.getDirector());
        movie.setGenre(response.getGenre());
        movie.setPosterUrl(response.getPosterUrl());
        movie.setDescription(response.getDescription());

        return movie;
    }

}
