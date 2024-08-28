package student.edu.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.edu.domain.model.User;
import student.edu.dto.UserDto;
import student.edu.dto.mapper.UserMapper;
import student.edu.service.UserService;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto){
        User user = userService.create(UserMapper.toUser(userDto));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(UserMapper.toDto(user));
    }

    @PutMapping("/addSeries/{userId}/{serieId}")
    public ResponseEntity<String> addSerie(@PathVariable("userId") Long userId, @PathVariable("serieId") Long serieId){
        userService.addSerie(userId, serieId);
        return ResponseEntity.ok().body("Serie added!");
    }

    @PutMapping("/addMovies/{userId}/{movieId}")
     public ResponseEntity<String> addMovie(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId){
        userService.addMovie(userId, movieId);
        return ResponseEntity.ok("Movie added!");
    }

    @PutMapping("removeSeries/{userId}/{serieId}")
    public ResponseEntity<String> removeSerie(@PathVariable("userId") Long userId, @PathVariable("serieId") Long serieId){
        userService.removeSerie(userId, serieId);
        return ResponseEntity.ok("Serie removed from user list!");
    }
}
