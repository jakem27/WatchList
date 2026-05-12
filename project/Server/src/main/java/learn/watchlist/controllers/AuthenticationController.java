package learn.watchlist.controllers;

import learn.watchlist.domain.Result;
import learn.watchlist.domain.ResultType;
import learn.watchlist.domain.AuthenticationService;
import learn.watchlist.models.User;
import learn.watchlist.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        Result<User> result = service.create(user);

        if(!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }

        String token = JwtUtil.generateToken(user.getUsername());
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Result<User> result = service.authenticate(user);

        if(result.isSuccess()) {
            String token = JwtUtil.generateToken(user.getUsername());

            return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
        } else if(result.getType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.UNAUTHORIZED);
        }
    }
}
