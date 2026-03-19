package io.github.dmitriirussu.petclinic.owner.port.out;

import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;

import java.util.List;
import java.util.Optional;

public interface OwnerReadRepository {
    Optional<Owner> findById(OwnerId id);
    List<Owner> findAll(int page, int pageSize);
    List<Owner> findByLastName(String lastName, int page, int pageSize);
    int countAll();
    int countByLastName(String lastName);
}
