package io.github.dmitriirussu.petclinic.vet.port.out;

import io.github.dmitriirussu.petclinic.vet.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;

import java.util.List;
import java.util.Optional;

public interface VetReadRepository {
    Optional<Vet> findById(VetId id);
    List<Vet> findAll(int page, int pageSize);
    int countAll();
}