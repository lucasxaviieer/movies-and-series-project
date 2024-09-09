package student.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import student.edu.controller.exceptions.ErrorMessage;
import student.edu.domain.model.Movie;
import student.edu.domain.model.Serie;
import student.edu.dto.SerieDto;
import student.edu.dto.mapper.SerieMapper;
import student.edu.service.SerieService;

import java.net.URI;
import java.util.List;

@Tag(name = "Series", description = "All operations related to serie, like insert, read and update.")
@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @Operation(summary = "Return all series", description = "Get all serie registered", responses = {
            @ApiResponse(responseCode = "200", description = "All series returned", content = @Content(mediaType = "application/json",
                   array = @ArraySchema(schema = @Schema(implementation = Serie.class)))),
            @ApiResponse(responseCode = "404", description = "No one series registered yet", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping
    public ResponseEntity<List<Serie>> findAll(){
        return ResponseEntity.ok(serieService.findAll());
    }
    @Operation(summary = "Find a serie", description = "Resource to find a serie by id", responses = {
            @ApiResponse(responseCode = "200", description = "Serie found successfully", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Serie.class))),
            @ApiResponse(responseCode = "404", description = "Serie not found", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Serie> findById(@PathVariable Long id){
        Serie serie = serieService.findById(id);
        return ResponseEntity.ok(serie);
    }

    @Operation(
            summary = "Insert a serie", description = "Resource to insert a new serie.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Serie inserted successfully", content = @Content(mediaType = "application/json"
                            , schema = @Schema(implementation = SerieDto.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data" ,content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<SerieDto> create(@Valid @RequestBody SerieDto serieDto){
        Serie serie = serieService.create(SerieMapper.toSerie(serieDto));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(serie.getId())
                .toUri();
        return ResponseEntity.created(location).body(SerieMapper.toDto(serie));
    }
}
