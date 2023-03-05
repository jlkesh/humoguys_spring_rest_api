package dev.jlkeesh.lesson_1_rest_api;

import java.util.UUID;

public record Book(UUID id, String title, String author) {
}
