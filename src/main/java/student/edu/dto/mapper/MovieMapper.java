package student.edu.dto.mapper;

import org.modelmapper.ModelMapper;
import student.edu.domain.model.Movie;
import student.edu.dto.MovieDto;

public class MovieMapper extends ModelMapper {

    public static MovieDto toDto(Movie movie){
        return new ModelMapper().map(movie, MovieDto.class);
    }

    public static Movie toMovie(MovieDto movieDto) {
        return new ModelMapper().map(movieDto, Movie.class);
    }
}
