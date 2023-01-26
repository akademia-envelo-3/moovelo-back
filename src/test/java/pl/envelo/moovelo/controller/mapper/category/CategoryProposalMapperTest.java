package pl.envelo.moovelo.controller.mapper.category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.envelo.moovelo.controller.dto.category.CategoryProposalDto;
import pl.envelo.moovelo.controller.mapper.actor.BasicUserMapper;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.categories.CategoryProposal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class CategoryProposalMapperTest {


    @Test
    void shouldMapCategoryProposalAndCategoryProposalDtoBothSides() {
        //given

        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
        BasicUser basicUser = new BasicUser();
        basicUser.setId(10L);
        basicUser.setFirstname("Jan");
        basicUser.setLastname("Pan");
        CategoryProposal categoryProposal = new CategoryProposal();
        categoryProposal.setId(5L);
        categoryProposal.setBasicUser(basicUser);
        categoryProposal.setName("programowanie");
        categoryProposal.setDescription("kategoria dot. wszystkich wydarzeń z zakresu programowania. Warsztaty, koferencje, etc.");
        categoryProposal.setDate(localDateTime.truncatedTo(ChronoUnit.SECONDS));

        //when
        CategoryProposalDto categoryProposalDto = CategoryProposalMapper.mapCategoryProposalToCategoryProposalDto(categoryProposal);
        CategoryProposal categoryProposalFromDto = CategoryProposalMapper.mapCategoryProposalDtoToCategoryProposal(categoryProposalDto);

        //then

        Assertions.assertEquals(categoryProposal, categoryProposalFromDto);
    }
}