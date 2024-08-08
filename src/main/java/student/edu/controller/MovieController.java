package student.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.edu.domain.model.Movie;
import student.edu.service.MovieService;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/movie")
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

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie){
        movie = movieService.create(movie);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movie.getId())
                .toUri();
        return ResponseEntity.created(location).body(movie);
    }

}
