package pl.envelo.moovelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class SortingAndPagingCriteria {
    private String sortBy = "id";
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private int pageNumber = 0;
    private int pageSize = 10;
}
