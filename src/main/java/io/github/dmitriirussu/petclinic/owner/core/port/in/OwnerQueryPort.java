package io.github.dmitriirussu.petclinic.owner.core.port.in;

import io.github.dmitriirussu.petclinic.kernel.pagination.Page;
import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;

import java.util.List;

public interface OwnerQueryPort {
    Owner findById(OwnerId id);
    Page<Owner> findPaginated(String lastName, int page, int pageSize);
}