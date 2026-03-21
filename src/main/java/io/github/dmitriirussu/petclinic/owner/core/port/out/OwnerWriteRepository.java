package io.github.dmitriirussu.petclinic.owner.core.port.out;

import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;

public interface OwnerWriteRepository {
    void save(Owner owner);
    void update(OwnerId id, Owner owner);
    void delete(OwnerId id);
}
