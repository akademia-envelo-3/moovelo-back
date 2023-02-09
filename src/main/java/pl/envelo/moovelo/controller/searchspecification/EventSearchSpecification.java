package pl.envelo.moovelo.controller.searchspecification;

import io.swagger.models.auth.In;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.EventInfo;
import pl.envelo.moovelo.entity.events.InternalEvent;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventSearchSpecification {

    public Specification<? extends Event> getEventsSpecification(String privacy, String group, String cat) {

        return (root, query, criteriaBuilder) -> {

            Root<InternalEvent> internalEventRoot = criteriaBuilder.treat(root, InternalEvent.class);

            List<Predicate> predicates = new ArrayList<>();

//            query.where(
//                    criteriaBuilder.and(
//                            criteriaBuilder.equal(root.type(), sublcass)
//                    )
//            );

            if(Objects.nonNull(privacy)) {

                if (privacy.equals("true")) {

                    predicates.add(
                            criteriaBuilder.isTrue(internalEventRoot.get("isPrivate"))
                    );
                }

                if (privacy.equals("false")) {


                    predicates.add(
                            criteriaBuilder.or(
                                    criteriaBuilder.like(root.get("eventInfo").get("name"), "%Ext%"),
                                    criteriaBuilder.isFalse(internalEventRoot.get("isPrivate"))

                                    )
                    );

                }
            }

            if (Objects.nonNull(group) && group.equals("true")) {


                predicates.add(

                                criteriaBuilder.isNotNull(criteriaBuilder.treat(root, InternalEvent.class).get("group"))
                );

            }



            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Sort.Order createSortOrder(String sort, String sortOrder){

        List<String> sortingParametres = List.of("name");
        if(!sortingParametres.contains(sort)) sort = "eventInfo_name";
        if(!sortOrder.equals("DESC")) sortOrder = "ASC";

        return new Sort.Order(Sort.Direction.valueOf(sortOrder), sort);
    }
}
