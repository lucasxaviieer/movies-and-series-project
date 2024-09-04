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
import student.edu.domain.model.Movie;
import student.edu.dto.MovieDto;
import student.edu.dto.mapper.MovieMapper;
import student.edu.service.MovieService;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "Movies" , description = "All operations related to serie, like insert, read and update.")
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> findAll(){
        List<Movie> movies = movieService.findAll();
        if(movies.isEmpty()){
            throw new NoSuchElementException("There are no one movies registered!");
        }
        return ResponseEntity.ok(movies);
    }

    @GetMapping("{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id){
        Movie movie = movieService.findById(id);
        return ResponseEntity.ok(movie);
    }

    @Operation(summary = "Insert a movie" , description = "Resource to insert a new Movie",
    responses = {
            @ApiResponse(responseCode = "201", description = "Movie inserted successfully.", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data", content = @Content(mediaType = "applcation/json",
            schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<MovieDto> create(@Valid @RequestBody MovieDto movieDto){
        Movie movie = movieService.create(MovieMapper.toMovie(movieDto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movie.getId())
                .toUri();
        return ResponseEntity.created(location).body(MovieMapper.toDto(movie));
    }

}
