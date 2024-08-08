package student.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.edu.domain.model.Serie;
import student.edu.service.SerieService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public ResponseEntity<List<Serie>> findAll(){
        return ResponseEntity.ok(serieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> findById(@PathVariable Long id){
        Serie serie = serieService.findById(id);
        return ResponseEntity.ok(serie);
    }

    @PostMapping
    public ResponseEntity<Serie> create(@RequestBody Serie serie){
        serie = serieService.create(serie);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(serie.getId())
                .toUri();
        return ResponseEntity.created(location).body(serie);
    }
}
