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

            predicatePrivacyIsTrue(privacy, root, criteriaBuilder, predicates);
            predicatePrivacyIsFalse(privacy, root, criteriaBuilder, predicates);
            predicateGroupNotNull(group, internalEventRoot, criteriaBuilder,  predicates);
            predicateCategoryLike(cat, root, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        log.info("EventSearchSpecification - getEventsSpecification() return {}", specification);
        return specification;
    }

    public Sort.Order createSortOrder(String sort, String sortOrder) {
        log.info("EventSearchSpecification - createSortOrder()");

        String sortParameter = getSortParameter(sort);
        String sortOrderParameter = getSortOrder(sortParameter, sortOrder);

        Sort.Order order = new Sort.Order(Sort.Direction.valueOf(sortOrderParameter), sortParameter);

        log.info("EventSearchSpecification - createSortOrder() return {}", order);
        return order;
    }

    private static void predicatePrivacyIsFalse(String privacy, Root<Event> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (Objects.nonNull(privacy) && privacy.equals("false")) {
            predicates.add(
                    criteriaBuilder.isFalse(root.get("isPrivate"))
            );
        }
    }

    private static void predicatePrivacyIsTrue(String privacy, Root<Event> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (Objects.nonNull(privacy) && privacy.equals("true")) {
            predicates.add(
                    criteriaBuilder.isTrue(root.get("isPrivate"))
            );
        }
    }

    private static void predicateGroupNotNull(String group, Root<InternalEvent> internalEventRoot,
                                              CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (Objects.nonNull(group) && group.equals("true")) {
            predicates.add(
                    criteriaBuilder.isNotNull(internalEventRoot.get("group"))
            );
        }
    }

    private static void predicateCategoryLike(String cat, Root<Event> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (Objects.nonNull(cat)) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("eventInfo").get("category").get("name")), cat.toLowerCase())
            );
        }
    }

    private static String getSortParameter(String sort) {
        List<String> sortingParameters = List.of("participants", "name", "location");

        if (sort == null || !sortingParameters.contains(sort)) {
            return "eventInfo_startDate";
        } else if (sort.equals("participants")) {
            return "numOfAcceptedStatusUsers";
        } else if (sort.equals("name")) {
            return "eventInfo_name";
        } else {
            return "eventInfo_location_city";
        }
    }

    private static String getSortOrder(String sortParameter, String sortOrder) {

        if (sortOrder == null) {
            if (sortParameter.equals("eventInfo_startDate")
                    || sortParameter.equals("numOfAcceptedStatusUsers")) {
                return "DESC";
            } else {
                return "ASC";
            }
        } else if (!sortOrder.equals("ASC") && !sortOrder.equals("DESC")) {
            return "ASC";
        } else {
            return sortOrder;
        }
    }

}
