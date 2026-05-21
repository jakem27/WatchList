package learn.watchlist.controllers;

import learn.watchlist.domain.ProfileService;
import learn.watchlist.domain.Result;
import learn.watchlist.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> friendProfile(@PathVariable("username") String findUsername, Authentication auth) {
        String authUsername = auth.getName();

        Result<User> result = service.findUser(authUsername, findUsername);
        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> userProfile(Authentication auth) {
        String username = auth.getName();

        Result<User> result = service.findUser(username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@RequestBody User user, Authentication auth) {
        String username = auth.getName();

        Result<Void> result = service.updateProfile(user, username);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
