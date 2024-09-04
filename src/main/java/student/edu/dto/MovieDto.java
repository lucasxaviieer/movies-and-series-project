package student.edu.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class MovieDto {

    @NotBlank(message = "Title can not be empty.")
    private String title;
    @NotNull
    @Min(value = 1000, message = "The year must have 4 digits.")
    @Max(value = 9999, message = "The year must have 4 digits.")
    private Integer year;

}
