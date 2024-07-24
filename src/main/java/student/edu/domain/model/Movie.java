package student.edu.domain.model;

import jakarta.persistence.*;


@Entity(name = "tb_movie")
public class Movie extends BaseVideoDetails {

    public Movie(){
    }

    public Movie(String title, Integer year) {
        super(title, year);
    }
}
