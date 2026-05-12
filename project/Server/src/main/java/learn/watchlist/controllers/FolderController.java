package learn.watchlist.controllers;

import learn.watchlist.domain.FolderService;
import learn.watchlist.domain.Result;
import learn.watchlist.models.Folder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/folder")
public class FolderController {
    private final FolderService service;

    public FolderController(FolderService service) {
        this.service = service;
    }

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
