package dev.jlkeesh.lesson_1_rest_api.domain;

import dev.jlkeesh.lesson_1_rest_api.dto.blog.BlogCreateDTO;
import dev.jlkeesh.lesson_1_rest_api.dto.blog.BlogDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlogMapper {
    Blog toEntity(BlogDTO dto);
    Blog toEntity(BlogCreateDTO dto);

    BlogDTO toDto(Blog blog);
    List<BlogDTO> toDto(List<Blog> blogs);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Blog partialUpdate(BlogDTO blogDTO, @MappingTarget Blog blog);
}