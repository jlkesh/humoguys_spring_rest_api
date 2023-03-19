package dev.jlkeesh.lesson_1_rest_api.controller.blog;


import dev.jlkeesh.lesson_1_rest_api.domain.Blog;
import dev.jlkeesh.lesson_1_rest_api.dto.blog.BlogCreateDTO;
import dev.jlkeesh.lesson_1_rest_api.dto.blog.BlogDTO;
import dev.jlkeesh.lesson_1_rest_api.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody BlogCreateDTO dto) {
        String id = blogService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BlogDTO>> getAll() {
        List<BlogDTO> blogs = blogService.getAll();
        return ResponseEntity.ok(blogs);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        blogService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable String id) {
        blogService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}
