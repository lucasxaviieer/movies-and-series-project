package student.edu.dto.mapper;

import org.modelmapper.ModelMapper;
import student.edu.domain.model.Serie;
import student.edu.dto.SerieDto;

public class SerieMapper extends ModelMapper {

    public static SerieDto toDto(Serie serie){
        return new ModelMapper().map(serie, SerieDto.class);
    }

    public static Serie toSerie(SerieDto serieDto) {
        return new ModelMapper().map(serieDto, Serie.class);
    }
}
