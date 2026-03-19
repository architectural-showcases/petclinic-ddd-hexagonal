package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.query;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.response.OwnerResponse;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerQueryPort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/owners")
public class RestOwnerQueryController {

    private static final int PAGE_SIZE = 5;

    private final OwnerQueryPort query;

    public RestOwnerQueryController(OwnerQueryPort query) {
        this.query = Objects.requireNonNull(query);
    }

    @GetMapping
    public List<OwnerResponse> findAll(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "1") int page
    ) {
        return query.findPaginated(lastName, page, PAGE_SIZE)
                .stream()
                .map(OwnerResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public OwnerResponse findById(@PathVariable String id) {
        return OwnerResponse.from(query.findById(OwnerId.of(id)));
    }
}