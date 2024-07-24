package student.edu.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_user")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @OneToMany
    @JsonIgnore
    private List<Movie> movies;

    @OneToMany
    @JsonIgnore
//    @JoinTable(name = "tb_user_serie",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "serie_id")
//    )
    private List<Serie> series;

}
