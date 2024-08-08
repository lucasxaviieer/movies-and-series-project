package student.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.edu.domain.model.User;
import student.edu.service.UserService;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        user = userService.create(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/addSerie/{userId}/{serieId}")
    public ResponseEntity<String> addSerie(@PathVariable("userId") Long userId, @PathVariable("serieId") Long serieId){
        userService.addSerie(userId, serieId);
        return ResponseEntity.ok().body("Serie added!");
    }

    @PutMapping("/addMovie/{userId}/{movieId}")
     public ResponseEntity<String> addMovie(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId){
        userService.addMovie(userId, movieId);
        return ResponseEntity.ok("Movie added!");
    }
}
