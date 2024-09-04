package student.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.edu.controller.exceptions.ErrorMessage;
import student.edu.domain.model.User;
import student.edu.dto.UserDto;
import student.edu.dto.mapper.UserMapper;
import student.edu.service.UserService;

import java.net.URI;

@Tag(name = "Users", description = "All operations related to user, like insert, read and update.")
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

    @Operation(summary = "Insert an user", description = "Resource to create a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User inserted successfully", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "409", description = "User email already registered", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    })

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
