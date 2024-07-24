package student.edu.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "tb_serie")
@Getter
@Setter
public class Serie extends BaseVideoDetails {

    @Column(nullable = false)
    private Integer seasons;

    public Serie(){
    }

    public Serie(Integer seasons, String title, Integer year) {
        super(title,year);
        this.seasons = seasons;
    }

}
