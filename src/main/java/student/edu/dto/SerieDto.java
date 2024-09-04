package student.edu.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class SerieDto {

    @NotBlank(message = "Title can not be empty.")
    private String title;
    @NotNull
    @Min(value = 1000, message = "The year must have 4 digits.")
    @Max(value = 9999, message = "The year must have 4 digits.")
    private Integer year;
    @NotNull(message = "Seasons cannot be null.")
    @Min(value = 1, message = "The season should be more than 1 or equals to 1.")
    private Integer seasons;

}
