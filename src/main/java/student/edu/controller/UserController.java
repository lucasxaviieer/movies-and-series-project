package student.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.edu.controller.exceptions.ErrorMessage;
import student.edu.domain.model.User;
import student.edu.dto.UserDto;
import student.edu.dto.mapper.UserMapper;
import student.edu.service.UserService;

import java.net.URI;
import java.util.List;

@Tag(name = "Users", description = "All operations related to user, like insert, read and update.")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Resource to get all users registered", responses = {
            @ApiResponse(responseCode = "200", description = "All users were returned successfully", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = User.class)))),
            @ApiResponse(responseCode = "404", description = "No one user found in database", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Find a user", description = "Resource to find a user by id", responses = {
            @ApiResponse(responseCode = "200", description = "User found successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
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

    @Operation(summary = "Add a serie", description = "Add serie to an user series list", responses = {
            @ApiResponse(responseCode = "204", description = "Serie added successfully", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "User or Serie not found", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Serie has already been added", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/addSeries/{userId}/{serieId}")
    public ResponseEntity<Void> addSerie(@PathVariable("userId") Long userId, @PathVariable("serieId") Long serieId){
        userService.addSerie(userId, serieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Add a movie", description = "Add a movie to an user movies list", responses = {
            @ApiResponse(responseCode = "204", description = "Movie added successfully", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "User or movie not found", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "409", description = "Movie has already been added", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("/addMovies/{userId}/{movieId}")
     public ResponseEntity<Void> addMovie(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId){
        userService.addMovie(userId, movieId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove a serie from user", description = "Remove a serie from user series list",responses = {
            @ApiResponse(responseCode = "204", description = "Serie removed successfully", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "User do not exists or serie is not in user series list", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PutMapping("removeSeries/{userId}/{serieId}")
    public ResponseEntity<Void> removeSerie(@PathVariable("userId") Long userId, @PathVariable("serieId") Long serieId){
        userService.removeSerie(userId, serieId);
        return ResponseEntity.noContent().build();
    }
}
