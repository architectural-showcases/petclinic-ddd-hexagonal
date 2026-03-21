package io.github.dmitriirussu.petclinic.kernel.pagination;

import java.util.List;

public record Page<T>(List<T> content, int total) {

    public int totalPages(int pageSize) {
        return (int) Math.ceil((double) total / pageSize);
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }
}
