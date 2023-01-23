package pl.envelo.moovelo.controller.dto;

import lombok.Builder;

@Builder
public class CategoryDto {
    private long id;
    private String name;
    private boolean isVisible;
}
