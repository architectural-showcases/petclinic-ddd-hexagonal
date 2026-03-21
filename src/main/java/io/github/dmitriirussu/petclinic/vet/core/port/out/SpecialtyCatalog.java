package io.github.dmitriirussu.petclinic.vet.core.port.out;


import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.Specialty;

import java.util.List;

public interface SpecialtyCatalog {
    Specialty     findByName(String name);
    List<Specialty> getAllSpecialties();
}
