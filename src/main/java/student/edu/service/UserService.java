package student.edu.service;


import student.edu.domain.model.User;


public interface UserService {

    User findById(Long id);

    User create(User user);

    void addSerie(Long userId, Long serieId);

    void addMovie(Long userId, Long movieId);

}
