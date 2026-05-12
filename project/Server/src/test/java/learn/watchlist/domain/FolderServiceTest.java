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
        folder.setParent_id(1);

        when(userRepository.findByUsername(folder.getUser().getUsername())).thenReturn(folder.getUser());
        when(folderRepository.findById(folder.getParent_id())).thenReturn(null);

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Parent not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddParentFolderDifferentUser() {
        Folder folder = TestHelper.makeFolder();
        folder.setParent_id(1);

        Folder parent = TestHelper.makeFolder();
        parent.setId(1);
        parent.getUser().setId(1);

        when(userRepository.findByUsername(folder.getUser().getUsername())).thenReturn(folder.getUser());
        when(folderRepository.findById(folder.getParent_id())).thenReturn(parent);

        Result<Folder> result = service.add(folder, folder.getUser().getUsername());
        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Parent and child folders must belong to same user", result.getMessages().get(0));
    }


}