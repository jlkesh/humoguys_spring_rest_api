package dev.jlkeesh.lesson_1_rest_api;

import dev.jlkeesh.lesson_1_rest_api.domain.auth.AuthRole;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthRoleRepository;
import dev.jlkeesh.lesson_1_rest_api.repository.AuthUserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@SpringBootApplication
@EnableAsync
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
public class BlogPostApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogPostApiApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AuthRoleRepository authRoleRepository) {
        return args -> {
            authRoleRepository.saveAll(List.of(
                    AuthRole.childBuilder().name("Admin").code("ADMIN").build(),
                    AuthRole.childBuilder().name("Manager").code("ADMIN").build(),
                    AuthRole.childBuilder().name("User").code("USER").build()
            ));
        };
    }

}
