package io.github.dmitriirussu.petclinic.vet.core.port.out;

import io.github.dmitriirussu.petclinic.vet.core.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;

public interface VetWriteRepository {
    void save(Vet vet);
    void update(VetId id, Vet vet);
    void delete(VetId id);
}
