package student.edu.service;

import student.edu.domain.model.Serie;

import java.util.List;

public interface SerieService {

    List<Serie> findAll();

    Serie findById(Long id);

    Serie create(Serie serie);

}
