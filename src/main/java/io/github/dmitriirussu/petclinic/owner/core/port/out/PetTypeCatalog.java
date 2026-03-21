package io.github.dmitriirussu.petclinic.owner.core.port.out;

import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetType;

import java.util.List;

public interface PetTypeCatalog {
    PetType findByName(String name);
    List<PetType> getAllTypes();
}
