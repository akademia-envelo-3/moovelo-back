package pl.envelo.moovelo.repository.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.model.EventsForUserCriteria;
import pl.envelo.moovelo.model.SortingAndPagingCriteria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class EventCriteriaRepository {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public Page<? extends Event> findAllEventsAvailableForUserWithFilters(
            Long userId,
            EventsForUserCriteria filterCriteria,
            SortingAndPagingCriteria sortingAndPagingCriteria
    ) {
        CriteriaQuery<? extends Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<? extends Event> eventRoot = criteriaQuery.from(Event.class);
        Predicate predicate = getPredicate(userId, filterCriteria, eventRoot);
        criteriaQuery.where(predicate);
        setOrder(sortingAndPagingCriteria, criteriaQuery, eventRoot);

        TypedQuery<? extends Event> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(sortingAndPagingCriteria.getPageNumber() * sortingAndPagingCriteria.getPageSize());
        typedQuery.setMaxResults(sortingAndPagingCriteria.getPageSize());

        Pageable pageable = getPageable(sortingAndPagingCriteria);
        long eventCount = getEventCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, eventCount);
    }

    private long getEventCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<? extends Event> countRoot = countQuery.from(Event.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(SortingAndPagingCriteria sortingAndPagingCriteria) {
        Sort sort = Sort.by(sortingAndPagingCriteria.getSortDirection(), sortingAndPagingCriteria.getSortBy());
        return PageRequest.of(sortingAndPagingCriteria.getPageNumber(), sortingAndPagingCriteria.getPageSize(), sort);
    }

    private void setOrder(SortingAndPagingCriteria sortingAndPagingCriteria, CriteriaQuery<? extends Event> criteriaQuery, Root<? extends Event> eventRoot) {
        if (sortingAndPagingCriteria.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(eventRoot.get(sortingAndPagingCriteria.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(eventRoot.get(sortingAndPagingCriteria.getSortBy())));
        }
    }

    private Predicate getPredicate(Long userId, EventsForUserCriteria filterCriteria, Root<? extends Event> eventRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (filterCriteria.isAcceptedEvents()) {
            predicates.add(
                    criteriaBuilder.like(
                            eventRoot.get("acceptedStatusUsers").get("id"),
                            userId.toString()
                    )
            );
        } else if (filterCriteria.isPendingEvents()) {
            predicates.add(
                    criteriaBuilder.like(
                            eventRoot.get("pendingStatusUsers").get("id"),
                            userId.toString()
                    )
            );
        } else if (filterCriteria.isRejectedEvents()) {
            predicates.add(
                    criteriaBuilder.like(
                            eventRoot.get("rejectedStatusUsers").get("id"),
                            userId.toString()
                    )
            );
        } else {
            predicates.add(
                    criteriaBuilder.like(
                            eventRoot.get("usersWithAccess").get("id"),
                            userId.toString()
                    )
            );
        }

        if (filterCriteria.isGroup()) {
            predicates.add(
                    criteriaBuilder.isNotEmpty(
                            eventRoot.get("group")
                    )
            );
        }

        if (filterCriteria.isOwner()) {
            predicates.add(
                    criteriaBuilder.like(
                            eventRoot.get("eventOwner").get("id"),
                            userId.toString()
                    )
            );
        }

        predicates.add(
                criteriaBuilder.like(
                        eventRoot.get("eventInfo").get("category").get("name"),
                        "%" + filterCriteria.getCategory() + "%"
                )
        );

        predicates.add(
                criteriaBuilder.like(
                        eventRoot.get("eventInfo").get("name"),
                        "%" + filterCriteria.getName() + "%"
                )
        );

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
