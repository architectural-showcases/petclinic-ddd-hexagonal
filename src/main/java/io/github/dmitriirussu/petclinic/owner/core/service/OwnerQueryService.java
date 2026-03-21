package io.github.dmitriirussu.petclinic.owner.core.service;

import io.github.dmitriirussu.petclinic.kernel.exception.EntityNotFoundException;
import io.github.dmitriirussu.petclinic.kernel.pagination.Page;
import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.port.in.OwnerQueryPort;
import io.github.dmitriirussu.petclinic.owner.core.port.out.OwnerReadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OwnerQueryService implements OwnerQueryPort {
    private final OwnerReadRepository read;

    public OwnerQueryService(OwnerReadRepository read) {
        this.read = Objects.requireNonNull(read);
    }

    @Override
    public Owner findById(OwnerId id) {
        return read.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found: " + id));
    }

    @Override
    public Page<Owner> findPaginated(String lastName, int page, int pageSize) {
        if (lastName == null || lastName.isBlank()) {
            return read.findAll(page, pageSize);
        }
        return read.findByLastName(lastName, page, pageSize);
    }
}
