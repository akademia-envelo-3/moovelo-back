package pl.envelo.moovelo.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.envelo.moovelo.entity.categories.CategoryProposal;

@Repository
public interface CategoryProposalRepository extends JpaRepository<CategoryProposal, Long> {
}