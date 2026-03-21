package io.github.dmitriirussu.petclinic.vet.core.port.out;

import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;

public interface VetIdGeneratorPort {
    VetId nextVetId();
}
