package io.github.dmitriirussu.petclinic.vet.port.out;


import io.github.dmitriirussu.petclinic.vet.domain.valueobject.Specialty;

import java.util.List;

public interface SpecialtyCatalog {
    Specialty     findByName(String name);
    List<Specialty> getAllSpecialties();
}
