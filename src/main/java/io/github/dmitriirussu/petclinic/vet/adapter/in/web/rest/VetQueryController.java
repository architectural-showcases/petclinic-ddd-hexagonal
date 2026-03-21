package io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest;

import io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.response.VetResponse;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.port.in.VetQueryPort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/vets")
public class VetQueryController {

    private static final int PAGE_SIZE = 5;

    private final VetQueryPort query;

    public VetQueryController(VetQueryPort query) {
        this.query = Objects.requireNonNull(query);
    }

    @GetMapping
    public List<VetResponse> findAll(
            @RequestParam(defaultValue = "1") int page
    ) {
        return query.findAll(page, PAGE_SIZE)
                .content()
                .stream()
                .map(VetResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public VetResponse findById(@PathVariable String id) {
        return VetResponse.from(query.findById(VetId.of(id)));
    }
}