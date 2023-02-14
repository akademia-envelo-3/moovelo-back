package pl.envelo.moovelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class SortingAndPagingCriteria {
    private String sortBy = "id";
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private int pageNumber = 0;
    private int pageSize = 10;
}
