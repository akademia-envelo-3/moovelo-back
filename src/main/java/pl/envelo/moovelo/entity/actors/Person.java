package pl.envelo.moovelo.entity.actors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Person {
    private String firstname;
    private String lastname;

    @Column(unique = true, nullable = false)
    private String email;
}

