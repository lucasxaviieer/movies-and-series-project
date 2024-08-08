package student.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.edu.domain.model.Movie;
import student.edu.domain.repository.MovieRepository;
import student.edu.domain.repository.UserRepository;
import student.edu.service.MovieService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource ID not found!"));
    }

    @Override
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
