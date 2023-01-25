package pl.envelo.moovelo.controller.mapper.category;

import pl.envelo.moovelo.controller.dto.category.CategoryProposalDto;
import pl.envelo.moovelo.entity.categories.CategoryProposal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

class CategoryProposalMapper {
    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static CategoryProposalDto mapCategoryToCategoryDto(CategoryProposal categoryProposal) {
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
  //      categoryProposal.setBasicUser(BasicUserMapper.mapBasicUserDtoToBasicUser(categoryProposalDto.getBasicUser()));
        categoryProposal.setName(categoryProposalDto.getName());
        categoryProposal.setDescription(categoryProposalDto.getDescription());
        categoryProposal.setDate(LocalDateTime.now(ZoneId.systemDefault()));
        return categoryProposal;
    }
}
