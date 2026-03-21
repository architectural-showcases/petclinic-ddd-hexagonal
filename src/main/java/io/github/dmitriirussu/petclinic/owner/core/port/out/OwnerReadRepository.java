package io.github.dmitriirussu.petclinic.owner.core.port.out;

import io.github.dmitriirussu.petclinic.kernel.pagination.Page;
import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;

import java.util.Optional;

public interface OwnerReadRepository {
    Optional<Owner> findById(OwnerId id);
    Page<Owner> findAll(int page, int pageSize);
    Page<Owner> findByLastName(String lastName, int page, int pageSize);
}
