package pl.envelo.moovelo.controller.searchUtils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class GroupPage {
    private int pageNumber = 0;
    private int pageSize = 10;
    private Sort.Direction sortOrder = Sort.Direction.DESC;
    private String sort = "groupSize";
}
