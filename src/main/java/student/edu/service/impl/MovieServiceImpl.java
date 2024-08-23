package student.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource ID not found!"));
    }

    @Transactional
    @Override
    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
