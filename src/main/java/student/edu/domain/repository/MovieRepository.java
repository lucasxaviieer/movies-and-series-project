package student.edu.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.edu.domain.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
