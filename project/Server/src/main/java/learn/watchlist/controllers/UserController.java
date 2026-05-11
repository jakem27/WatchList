package learn.watchlist.controllers;

import learn.watchlist.domain.Result;
import learn.watchlist.domain.UserService;
import learn.watchlist.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User user) {
        Result<User> result = service.create(user);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
