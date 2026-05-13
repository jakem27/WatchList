package learn.watchlist.domain;

import learn.watchlist.TestHelper;
import learn.watchlist.data.FolderRepository;
import learn.watchlist.data.UserRepository;
import learn.watchlist.models.Folder;
import learn.watchlist.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FolderServiceTest {

    @Autowired
    FolderService service;

    @MockitoBean
    FolderRepository folderRepository;

    @MockitoBean
    UserRepository userRepository;

    @Test
    void shouldAdd() {
        Folder folder = TestHelper.makeFolder();
        Folder expected = TestHelper.makeFolder();


        when(userRepository.findByUsername(folder.getUser().getUsername())).thenReturn(folder.getUser());
        when(folderRepository.add(folder)).thenReturn(expected);

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotAddSetId() {
        Folder folder = TestHelper.makeFolder();
        folder.setId(1);

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Id should not be set for `add` method", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddBlankName() {
        Folder folder = TestHelper.makeFolder();
        folder.setName("");

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Name is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddRootName() {
        Folder folder = TestHelper.makeFolder();
        folder.setName("root");

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Folder name `root` is unavailable", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddUserDoesNotExist() {
        Folder folder = TestHelper.makeFolder();

        when(userRepository.findByUsername(folder.getUser().getUsername())).thenReturn(null);

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Invalid user", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddParentFolderDoesNotExist() {
        Folder folder = TestHelper.makeFolder();
        folder.setParentId(1);

        when(userRepository.findByUsername(folder.getUser().getUsername())).thenReturn(folder.getUser());
        when(folderRepository.findById(folder.getParentId())).thenReturn(null);

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Parent not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddParentFolderDifferentUser() {
        Folder folder = TestHelper.makeFolder();
        folder.setParentId(1);

        Folder parent = TestHelper.makeFolder();
        parent.setId(1);
        parent.getUser().setId(1);

        when(userRepository.findByUsername(folder.getUser().getUsername())).thenReturn(folder.getUser());
        when(folderRepository.findById(folder.getParentId())).thenReturn(parent);

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Parent and child folders must belong to same user", result.getMessages().get(0));
    }

    @Test
    void shouldNotFindRootUserDoesNotExist() {
        when(userRepository.findByUsername("username")).thenReturn(null);

        Result<Folder> result = service.findRoot("username");
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Invalid user", result.getMessages().get(0));
    }

    @Test
    void shouldNotFindChildrenUserDoesNotExist() {
        when(userRepository.findByUsername("username")).thenReturn(null);

        Result<List<Folder>> result = service.findChildren(1, "username");
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Invalid user", result.getMessages().get(0));
    }

    @Test
    void shouldNotFindChildrenWrongUser() {
        Folder folder = TestHelper.makeFolder();
        folder.setId(1);
        User user = TestHelper.makeUser();

        when(userRepository.findByUsername("username")).thenReturn(user);
        when(folderRepository.findById(1)).thenReturn(folder);

        Result<List<Folder>> result = service.findChildren(1, "username");
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Cannot access someone else's folder", result.getMessages().get(0));
    }

}