package io.github.dmitriirussu.petclinic.vet.port.out;

import io.github.dmitriirussu.petclinic.vet.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;

public interface VetWriteRepository {
    void save(Vet vet);
    void update(VetId id, Vet vet);
    void delete(VetId id);
}
