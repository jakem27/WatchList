package learn.watchlist.controllers;

import learn.watchlist.domain.FolderService;
import learn.watchlist.domain.MovieFolderService;
import learn.watchlist.domain.Result;
import learn.watchlist.models.Folder;
import learn.watchlist.models.Movie;
import learn.watchlist.models.MovieFolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("/root")
    public ResponseEntity<?> findRoot(Authentication auth) {
        String username = auth.getName();
        Result<Folder> result = folderService.findRoot(username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllByUser(Authentication auth) {
        String username = auth.getName();
        Result<List<Folder>> result = folderService.findAllByUser(username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping("/{id}/children")
    public ResponseEntity<?> findChildren(@PathVariable("id") int id, Authentication auth) {
        String username = auth.getName();
        Result<List<Folder>> result = folderService.findChildren(id, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> findFriendsFolders(Authentication auth) {
        String username = auth.getName();
        Result<List<Folder>> result = folderService.findFriendsFolders(username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Folder folder, Authentication auth) {
        String username = auth.getName();
        Result<Folder> result = folderService.add(folder, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Folder folder, Authentication auth) {
        String username = auth.getName();
        Result<Void> result = folderService.update(folder, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Folder folder, Authentication auth) {
        String username = auth.getName();
        Result<Void> result = folderService.delete(folder, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
