package student.edu.domain.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseVideoDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300, nullable = false)
    private String title;

    @Column(nullable = false, name = "yearValue")
    private Integer year;

    public BaseVideoDetails() {
    }

    public BaseVideoDetails(String title, Integer year) {
        this.id = null;
        this.title = title;
        this.year = year;
    }
}
