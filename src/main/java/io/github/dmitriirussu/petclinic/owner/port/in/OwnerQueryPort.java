package io.github.dmitriirussu.petclinic.owner.port.in;

import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;

import java.util.List;

public interface OwnerQueryPort {
    Owner findById(OwnerId id);
    List<Owner> findPaginated(String lastName, int page, int pageSize);
    int countByLastName(String lastName);
}