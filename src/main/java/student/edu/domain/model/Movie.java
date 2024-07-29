package student.edu.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "tb_movie")
public class Movie extends BaseVideoDetails {

    @ManyToMany(mappedBy = "movies")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @Deprecated
    public Movie(){
    }

    public Movie(String title, Integer year) {
        super(title, year);
    }


}
