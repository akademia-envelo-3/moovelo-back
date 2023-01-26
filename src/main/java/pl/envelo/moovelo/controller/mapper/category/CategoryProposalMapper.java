package pl.envelo.moovelo.controller.mapper.category;

import pl.envelo.moovelo.controller.dto.category.CategoryProposalDto;
import pl.envelo.moovelo.entity.categories.CategoryProposal;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static pl.envelo.moovelo.Constants.DATE_FORMAT;

class CategoryProposalMapper {

    public static CategoryProposalDto mapCategoryProposalToCategoryProposalDto(CategoryProposal categoryProposal) {
        return CategoryProposalDto.builder()
                .id(categoryProposal.getId())
             //   .basicUser(BasicUserMapper.mapBasicUserToBasicUserDto(categoryProposal.getBasicUser())
                .name(categoryProposal.getName())
                .description(categoryProposal.getDescription())
                .date(categoryProposal.getDate().format(DATE_FORMAT))
                .build();
    }

    public static CategoryProposal mapCategoryProposalDtoToCategoryProposal(CategoryProposalDto categoryProposalDto) {
        CategoryProposal categoryProposal = new CategoryProposal();
        categoryProposal.setId(categoryProposalDto.getId());
  //      categoryProposal.setBasicUser(BasicUserMapper.mapBasicUserDtoToBasicUser(categoryProposalDto.getBasicUser()));
        categoryProposal.setName(categoryProposalDto.getName());
        categoryProposal.setDescription(categoryProposalDto.getDescription());
        categoryProposal.setDate(LocalDateTime.now(ZoneId.systemDefault()));
        return categoryProposal;
    }
}
