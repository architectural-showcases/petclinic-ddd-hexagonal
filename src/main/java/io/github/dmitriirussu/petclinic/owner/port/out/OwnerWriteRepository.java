package io.github.dmitriirussu.petclinic.owner.port.out;

import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;

public interface OwnerWriteRepository {
    void save(Owner owner);
    void update(OwnerId id, Owner owner);
    void delete(OwnerId id);
}
