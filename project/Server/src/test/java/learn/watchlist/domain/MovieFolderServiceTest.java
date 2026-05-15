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

import java.util.List;

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
    void shouldFindMovies() {
        when(userRepository.findByUsername("test user")).thenReturn(new User());
        when(folderRepository.findById(1)).thenReturn(TestHelper.makeFolder());
        when(movieFolderRepository.findByFolderId(1)).thenReturn(List.of());

        Result<List<MovieFolder>> result = service.findByFolderId(1, "test user");

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotFindMoviesUserDoesNotExist() {
        when(userRepository.findByUsername("fake")).thenReturn(null);

        Result<List<MovieFolder>> result = service.findByFolderId(1, "fake");

        assertEquals("Invalid user", result.getMessages().get(0));
    }

    @Test
    void shouldNotFindFolderDoesNotExist() {
        when(userRepository.findByUsername("test user")).thenReturn(TestHelper.makeUser());
        when(folderRepository.findById(1)).thenReturn(null);

        Result<List<MovieFolder>> result = service.findByFolderId(1, "test user");

        assertEquals("Folder does not exist", result.getMessages().get(0));
    }

    @Test
    void shouldNotFindMoviesWrongUser() {
        User user = TestHelper.makeUser();
        user.setUsername("wrong name");
        when(userRepository.findByUsername("wrong name")).thenReturn(user);
        when(folderRepository.findById(1)).thenReturn(TestHelper.makeFolder());

        Result<List<MovieFolder>> result = service.findByFolderId(1, "wrong name");

        assertEquals("Cannot access someone else's folder", result.getMessages().get(0));
    }

    @Test
    void shouldAdd() {
        Movie movie = TestHelper.makeMovie();
        Folder folder = TestHelper.makeFolder();
        folder.setId(1);
        User user = folder.getUser();

        MovieFolder movieFolder = new MovieFolder(movie, folder);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(folderRepository.findById(folder.getId())).thenReturn(folder);
        when(movieRepository.findByTitle(movie.getTitle())).thenReturn(movie);
        when(movieFolderRepository.add(movieFolder)).thenReturn(true);

        Result<Void> result = service.add(movieFolder, user.getUsername());
        assertTrue(result.isSuccess());
    }
}