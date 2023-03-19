package dev.jlkeesh.lesson_1_rest_api.service;

import dev.jlkeesh.lesson_1_rest_api.domain.Blog;
import dev.jlkeesh.lesson_1_rest_api.domain.BlogMapper;
import dev.jlkeesh.lesson_1_rest_api.dto.blog.BlogCreateDTO;
import dev.jlkeesh.lesson_1_rest_api.dto.blog.BlogDTO;
import dev.jlkeesh.lesson_1_rest_api.repository.BlogRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public record BlogService(BlogRepository blogRepository, BlogMapper blogMapper) {


    public String create(@NonNull BlogCreateDTO dto) {
        Blog blog = blogMapper.toEntity(dto);
        blogRepository.save(blog);
        return blog.getId();
    }

    public List<BlogDTO> getAll() {
        List<Blog> blogs = blogRepository.getAllByDeletedFalse();
        List<BlogDTO> blogDTOS = blogMapper.toDto(blogs);
        return blogDTOS;
    }

    public void delete(@NonNull String id) {
        blogRepository.deleteById(id);
    }

    public void softDelete(@NonNull String id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog Not Found"));
        blog.setDeleted(true);
        blogRepository.save(blog);
    }
}
