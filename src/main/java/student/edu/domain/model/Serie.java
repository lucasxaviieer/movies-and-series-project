package student.edu.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "tb_serie")
@Getter
@Setter
public class Serie extends BaseVideoDetails {

    @Column(nullable = false)
    private Integer seasons;

    @ManyToMany(mappedBy = "series", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
