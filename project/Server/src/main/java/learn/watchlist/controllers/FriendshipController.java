package learn.watchlist.controllers;

import learn.watchlist.domain.FriendshipService;

import learn.watchlist.domain.Result;
import learn.watchlist.domain.ResultType;
import learn.watchlist.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendship")
public class FriendshipController {
    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findFriends(Authentication auth) {
        String username = auth.getName();
        Result<List<User>> result = service.findFriends(username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<?> findRequests(Authentication auth) {
        String username = auth.getName();
        Result<List<User>> result = service.findRequests(username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @PostMapping("/{friendUsername}")
    public ResponseEntity<?> addRequest(@PathVariable("friendUsername") String friendUsername, Authentication auth) {
        String username = auth.getName();
        Result<Void> result = service.addRequest(username, friendUsername);

        if(result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        } else if(result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/accept/{friendUsername}")
    public ResponseEntity<?> acceptRequest(@PathVariable("friendUsername") String friendUsername, Authentication auth) {
        String username = auth.getName();
        Result<Void> result = service.acceptRequest(username, friendUsername);

        if(result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        } else if(result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{friendUsername}")
    public ResponseEntity<?> deleteFriendship(@PathVariable("friendUsername") String friendUsername, Authentication auth) {
        String username = auth.getName();
        Result<Void> result = service.delete(username, friendUsername);

        if(result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        } else if(result.getType() == ResultType.INVALID) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
