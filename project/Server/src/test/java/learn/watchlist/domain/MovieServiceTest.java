package learn.watchlist.domain;

import learn.watchlist.data.MovieJdbcClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    @Autowired
    MovieService service;

    @MockitoBean
    MovieJdbcClientRepository repository;


}