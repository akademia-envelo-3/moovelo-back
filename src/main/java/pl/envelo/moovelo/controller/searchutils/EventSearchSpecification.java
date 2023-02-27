package pl.envelo.moovelo.controller.searchutils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.model.EventsForUserCriteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EventSearchSpecification {

    public Specification<Event> getEventsSpecification(String privacy, String group, String cat, Long groupId) {
        log.info("EventSearchSpecification - getEventsSpecification()");

        Specification<Event> specification = (root, query, criteriaBuilder) -> {

            Root<InternalEvent> internalEventRoot = criteriaBuilder.treat(root, InternalEvent.class);

            List<Predicate> predicates = new ArrayList<>();

            predicateByPrivacy(privacy, root, criteriaBuilder, predicates);
            predicateGroupNotNull(group, internalEventRoot, criteriaBuilder, predicates);
            predicateByGroupId(groupId, internalEventRoot, criteriaBuilder, predicates);
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

    private static void predicateByPrivacy(String privacy, Root<Event> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (Objects.nonNull(privacy)) {
            if (privacy.equalsIgnoreCase("true")) {
                predicates.add(
                        criteriaBuilder.isTrue(root.get("isPrivate"))
                );
            } else if (privacy.equalsIgnoreCase("false")) {
                predicates.add(
                        criteriaBuilder.isFalse(root.get("isPrivate"))
                );
            }
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

    private static void predicateByGroupId(Long groupId, Root<InternalEvent> internalEventRoot,
                                           CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (Objects.nonNull(groupId)) {
            predicates.add(
                    criteriaBuilder.equal(internalEventRoot.get("group").get("id"), groupId)
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

    public Sort.Order createSortOrderForUserSpecification(String sortBy, Sort.Direction sortDirection) {
        log.info("EventSearchSpecification - createSortOrderForUserSpecification(sortBy = '{}', sortDirection = '{}')", sortBy, sortDirection);
        String sortOrderParameter = switch (sortBy.toLowerCase()) {
            case "name" -> "eventInfo_name";
            case "date" -> "eventInfo_startDate";
            case "participants" -> "numOfAcceptedStatusUsers";
            case "location" -> "eventInfo_location_city";
            default -> "eventInfo_startDate";
        };

        Sort.Order order = new Sort.Order(sortDirection, sortOrderParameter);
        log.info("EventSearchSpecification - createSortOrderForUserSpecification(sortBy = '{}', sortDirection = '{}') - return order = '{}'",
                sortBy, sortDirection, order);
        return order;
    }

    public Specification<Event> getEventsAvailableForUserSpecification(Long userId, EventsForUserCriteria filterCriteria) {
        log.info("EventSearchSpecification - getEventsAvailableForUserSpecification(userId = '{}', filterCriteria = '{}')",
                userId, filterCriteria);

        Specification<Event> specification = (root, query, criteriaBuilder) -> {
            Root<InternalEvent> internalEventRoot = criteriaBuilder.treat(root, InternalEvent.class);
            List<Predicate> predicates = new ArrayList<>();

            getEventsAvailableForUserPredicates(userId, filterCriteria, root, criteriaBuilder, predicates);
            getInternalEventsAvailableForUserPredicates(filterCriteria, internalEventRoot, criteriaBuilder, predicates);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        log.info("EventSearchSpecification - getEventsAvailableForUserSpecification(userId = '{}', filterCriteria = '{}') return '{}'",
                userId, filterCriteria, specification);

        return specification;
    }

    private void getEventsAvailableForUserPredicates(
            Long userId,
            EventsForUserCriteria filterCriteria,
            Root<Event> eventRoot,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates
    ) {
        predicates.add(
                criteriaBuilder.in(
                        eventRoot.join("usersWithAccess").get("id")
                ).value(userId)
        );

        if (filterCriteria.isAcceptedEvents()) {
            predicates.add(
                    criteriaBuilder.in(
                            eventRoot.join("acceptedStatusUsers").get("id")
                    ).value(userId)
            );
        }

        if (filterCriteria.isPendingEvents()) {
            predicates.add(
                    criteriaBuilder.in(
                            eventRoot.join("pendingStatusUsers").get("id")
                    ).value(userId)
            );
        }

        if (filterCriteria.isRejectedEvents()) {
            predicates.add(
                    criteriaBuilder.in(
                            eventRoot.join("rejectedStatusUsers").get("id")
                    ).value(userId)
            );
        }

        if (filterCriteria.isOwner()) {
            predicates.add(
                    criteriaBuilder.equal(
                            eventRoot.get("eventOwner").get("id"),
                            userId
                    )
            );
        }

        if (filterCriteria.getCategory() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("eventInfo").get("category").get("name")),
                            "%" + filterCriteria.getCategory().toLowerCase() + "%"
                    )
            );
        }

        if (filterCriteria.getName() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("eventInfo").get("name")),
                            "%" + filterCriteria.getName().toLowerCase() + "%"
                    )
            );
        }
    }

    private void getInternalEventsAvailableForUserPredicates(
            EventsForUserCriteria filterCriteria,
            Root<InternalEvent> eventRoot,
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates
    ) {
        if (filterCriteria.isGroup()) {
            predicates.add(
                    criteriaBuilder.isNotNull(
                            eventRoot.get("group")
                    )
            );
        }

        if (filterCriteria.getGroupId() != null) {
            predicates.add(
                    criteriaBuilder.equal(
                            eventRoot.get("group").get("id"),
                            filterCriteria.getGroupId()
                    )
            );
        }

        if (filterCriteria.getGroupName() != null) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(eventRoot.get("group").get("groupInfo").get("name")),
                            "%" + filterCriteria.getGroupName().toLowerCase() + "%"
                    )
            );
        }
    }
}