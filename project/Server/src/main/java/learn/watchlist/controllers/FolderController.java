package learn.watchlist.controllers;

import learn.watchlist.domain.FolderService;
import learn.watchlist.domain.Result;
import learn.watchlist.models.Folder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService service;

    public FolderController(FolderService service) {
        this.service = service;
    }

    @GetMapping("/root")
    public ResponseEntity<?> findRoot(Authentication auth) {
        String username = auth.getName();
        Result<List<Folder>> result = service.findRoot(username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @GetMapping("/{id}/children")
//    public ResponseEntity<?> findChildren(@PathVariable("id") int id, Authentication auth) {
//        String username = auth.getName();
//        Result<List<Folder>> result = service.findChildren(id, username);
//
//        if(!result.isSuccess()) {
//            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Folder folder, Authentication auth) {
        String username = auth.getName();
        Result<Folder> result = service.add(folder, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }
}
