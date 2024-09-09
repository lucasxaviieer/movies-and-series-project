package student.edu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.edu.domain.model.Serie;
import student.edu.domain.repository.SerieRepository;
import student.edu.exception.SerieNotFoundException;
import student.edu.service.SerieService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SerieServiceImpl implements SerieService {

    @Autowired
    private SerieRepository serieRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Serie> findAll() {
        List<Serie> series = serieRepository.findAll();
        if(series.isEmpty()) {
            throw new SerieNotFoundException("There are no one series registered!");
        }
        return series;
    }

    @Transactional(readOnly = true)
    @Override
    public Serie findById(Long id) {
        return serieRepository.findById(id).orElseThrow(() -> new SerieNotFoundException("Resource ID not found!"));
    }

    @Transactional
    @Override
    public Serie create(Serie serie) {
        serie = serieRepository.save(serie);
        return serie;
    }
}
