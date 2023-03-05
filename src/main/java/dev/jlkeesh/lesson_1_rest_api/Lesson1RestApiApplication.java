package dev.jlkeesh.lesson_1_rest_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Study Project",
                description = "This Project just for Learning Purpose",
                termsOfService = "https://online.pdp.uz",
                contact = @Contact(
                        name = "Javohir Elmurodov",
                        url = "https://github.com/jlkesh",
                        email = "john@gmail.com"
                )
        ),
        tags = {
                @Tag(name = "Book-Controller-Tag", description = "You can do xzzzzzzzzzzzzzzzzzzzzzzzzzzzz")
        }
)
public class Lesson1RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lesson1RestApiApplication.class, args);
    }

}
