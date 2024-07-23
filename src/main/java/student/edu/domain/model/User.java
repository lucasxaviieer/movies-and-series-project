package student.edu.domain.model;

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

    @Column(length = 100, nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Movie> movies;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Serie> series;
}
