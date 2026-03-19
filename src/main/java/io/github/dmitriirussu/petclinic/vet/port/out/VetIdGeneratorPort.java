package io.github.dmitriirussu.petclinic.vet.port.out;

import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;

public interface VetIdGeneratorPort {
    VetId nextVetId();
}
