package student.edu.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.edu.domain.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {

}
