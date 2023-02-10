package pl.envelo.moovelo.controller.searchspecification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.InternalEvent;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EventSearchSpecification {

    public Specification<Event> getEventsSpecification(String privacy, String group, String cat) {
        log.info("EventSearchSpecification - getEventsSpecification()");

        Specification<Event> specification = (root, query, criteriaBuilder) -> {

            Root<InternalEvent> internalEventRoot = criteriaBuilder.treat(root, InternalEvent.class);

            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(privacy)) {

                if (privacy.equals("true")) {
                    predicates.add(
                            criteriaBuilder.isTrue(root.get("isPrivate"))
                    );
                }

                if (privacy.equals("false")) {
                    predicates.add(
                            criteriaBuilder.isFalse(root.get("isPrivate"))
                    );
                }
            }

            if (Objects.nonNull(group) && group.equals("true")) {
                predicates.add(
                        criteriaBuilder.isNotNull(internalEventRoot.get("group"))
                );
            }

            if (Objects.nonNull(cat)) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("eventInfo").get("category").get("name")), cat.toLowerCase())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        log.info("EventSearchSpecification - getEventsSpecification() return {}", specification);
        return specification;
    }

    public Sort.Order createSortOrder(String sort, String sortOrder) {
        log.info("EventSearchSpecification - createSortOrder()");
        List<String> sortingParameters = List.of("participants", "name", "location");

        if (sort == null || !sortingParameters.contains(sort)) {
            sort = "eventInfo_startDate";
            if (sortOrder == null) {
                sortOrder = "DESC";
            }
        }

        if (sort.equals("participants")) {
            sort = "numOfAcceptedStatusUsers";
            if (sortOrder == null) {
                sortOrder = "DESC";
            }
        }

        if (sort.equals("name")) {
            sort = "eventInfo_name";
            if (sortOrder == null) {
                sortOrder = "ASC";
            }
        }

        if (sort.equals("location")) {
            sort = "eventInfo_location_city";
            if (sortOrder == null) {
                sortOrder = "ASC";
            }
        }

        if (!sortOrder.equals("ASC") && !sortOrder.equals("DESC")) {
            sortOrder = "ASC";
        }

        Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sortOrder), sort);

        log.info("EventSearchSpecification - createSortOrder() return {}", order);
        return order;
    }
}
