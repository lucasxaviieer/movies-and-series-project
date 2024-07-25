package student.edu.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "tb_serie")
@Getter
@Setter
public class Serie extends BaseVideoDetails {

    @Column(nullable = false)
    private Integer seasons;

    @ManyToMany(mappedBy = "series")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @Deprecated
    public Serie(){
    }

    public Serie(Integer seasons, String title, Integer year) {
        super(title,year);
        this.seasons = seasons;
    }

}
