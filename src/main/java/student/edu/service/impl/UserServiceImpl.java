package student.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import student.edu.domain.model.Movie;
import student.edu.domain.model.Serie;
import student.edu.domain.model.User;
import student.edu.domain.repository.MovieRepository;
import student.edu.domain.repository.SerieRepository;
import student.edu.domain.repository.UserRepository;
import student.edu.service.UserService;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SerieRepository serieRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Resource ID not found!"));
    }

    @Override
    public User create(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("This email already exists!");
        }
        return userRepository.save(user);
    }

    @Override
    public void addSerie(Long userId, Long serieId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("This user ID do not exists!"));
        Serie serie = serieRepository.findById(serieId).orElseThrow(() -> new NoSuchElementException("This serie ID do not exists!"));

        Serie serieCheck = user.getSeries().stream().filter(x -> x.getId().equals(serie.getId())).findFirst().orElse(null);
        if(serieCheck != null){
            throw new IllegalArgumentException("This serie has already been added!");
        }
        user.getSeries().add(serie);
        userRepository.save(user);
    }

    @Override
    public void addMovie(Long userId, Long movieId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("This user ID do not exists!"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NoSuchElementException("This movie ID do not exists!"));

        Movie movieCheck = user.getMovies().stream().filter(x -> x.getId().equals(movie.getId())).findFirst().orElse(null);
        if(movieCheck != null){
            throw new IllegalArgumentException("This serie has already been added!");
        }
        user.getMovies().add(movie);
        userRepository.save(user);
    }

    @Override
    public void removeSerie(Long userId, Long serieId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("This user ID do not exists!"));
        Serie serieCheck = user.getSeries().stream().filter(x -> x.getId().equals(serieId)).findFirst().orElse(null);

        if(serieCheck == null) {
            throw new NoSuchElementException("This serie is not in user series list");
        }

        user.getSeries().remove(serieCheck);
        userRepository.save(user);
    }
}
