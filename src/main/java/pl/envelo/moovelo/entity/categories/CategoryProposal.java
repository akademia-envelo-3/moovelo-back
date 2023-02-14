package pl.envelo.moovelo.entity.categories;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.envelo.moovelo.entity.actors.BasicUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private BasicUser basicUser;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CategoryProposal that = (CategoryProposal) o;
        return Objects.equals(id, that.id) && Objects.equals(basicUser, that.basicUser) && Objects.equals(name, that.name)
                && Objects.equals(description, that.description) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basicUser, name, description, date);
    }
}
