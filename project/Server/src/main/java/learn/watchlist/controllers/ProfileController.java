package learn.watchlist.controllers;

import learn.watchlist.domain.ProfileService;
import learn.watchlist.domain.Result;
import learn.watchlist.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findUser(@PathVariable("username") String findUsername, Authentication auth) {
        String authUsername = auth.getName();

        Result<User> result;
        if(findUsername.equals(authUsername)) {
            result = service.findUser(authUsername);
        } else {
            result = service.findUser(authUsername, findUsername);
        }

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }
}
