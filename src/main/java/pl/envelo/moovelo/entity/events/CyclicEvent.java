package pl.envelo.moovelo.entity.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CyclicEvent extends InternalEvent {

    private Integer frequencyInDays;

    private Integer numberOfRepeats;
}
