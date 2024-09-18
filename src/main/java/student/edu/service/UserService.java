package student.edu.service;


import student.edu.domain.model.User;

import java.util.List;


public interface UserService {

    User findById(Long id);

    User create(User user);

    void addSerie(Long userId, Long serieId);

    void addMovie(Long userId, Long movieId);

    void removeSerie(Long userId, Long serieId);

    List<User> findAll();
}
