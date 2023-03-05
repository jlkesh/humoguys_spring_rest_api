package dev.jlkeesh.lesson_1_rest_api;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private List<Book> books = new ArrayList<>() {{
        add(new Book(UUID.randomUUID(), "Otamdan Qolgan Dalalar", "Primqul Qodirov"));
        add(new Book(UUID.randomUUID(), "Yulduzli Tunlar", "Primqul Qodirov"));
        add(new Book(UUID.randomUUID(), "Sariq Devni Minib", "Xudoyberdi To''xtaboyev"));
    }};


    @GetMapping
    public List<Book> getAll() {
        return books;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book save(@RequestBody BookCreateDTO dto) {
        Book book = new Book(UUID.randomUUID(), dto.title(), dto.author());
        books.add(book);
        return book;
    }

    @PutMapping("/{id}")
    public Book update(@RequestBody BookUpdateDTO dto, @PathVariable UUID id) {
        if (books.removeIf(book -> book.id().equals(id))) {
            Book newBook = new Book(UUID.randomUUID(), dto.title(), dto.author());
            books.add(newBook);
            return newBook;
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        books.removeIf(book -> book.id().equals(id));
    }

}
