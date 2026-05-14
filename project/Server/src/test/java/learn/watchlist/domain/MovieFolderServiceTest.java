package learn.watchlist.domain;

import learn.watchlist.TestHelper;
import learn.watchlist.data.FolderRepository;
import learn.watchlist.data.MovieFolderRepository;
import learn.watchlist.data.MovieRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Folder;
import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;
import learn.watchlist.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MovieFolderServiceTest {

    @Autowired
    MovieFolderService service;

    @MockitoBean
    MovieFolderRepository movieFolderRepository;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    FolderRepository folderRepository;

    @MockitoBean
    MovieRepository movieRepository;

    @Test
    void shouldAdd() {
        Movie movie = TestHelper.makeMovie();
        Folder folder = TestHelper.makeFolder();
        folder.setId(1);
        User user = folder.getUser();

        MovieFolder movieFolder = new MovieFolder(folder.getId(), movie);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(folderRepository.findById(folder.getId())).thenReturn(folder);
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(movie);
        when(movieFolderRepository.add(movieFolder)).thenReturn(true);

        Result<Void> result = service.add(movieFolder, user.getUsername());
        assertTrue(result.isSuccess());
    }
}