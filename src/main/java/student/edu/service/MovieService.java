package student.edu.service;

import student.edu.domain.model.Movie;

import java.util.List;

public interface MovieService {

    Movie findById(Long id);

    Movie create(Movie movie);

    List<Movie> findAll();
}
