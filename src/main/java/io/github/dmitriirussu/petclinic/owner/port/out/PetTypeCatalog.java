package io.github.dmitriirussu.petclinic.owner.port.out;

import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetType;

import java.util.List;

public interface PetTypeCatalog {
    PetType findByName(String name);
    List<PetType> getAllTypes();
}
