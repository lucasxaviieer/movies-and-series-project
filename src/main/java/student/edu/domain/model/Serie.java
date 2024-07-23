package student.edu.domain.model;


import jakarta.persistence.*;


@Entity(name = "tb_serie")
public class Serie extends BaseVideoDetails {

    @Column(nullable = false)
    private Integer seasons;

}
