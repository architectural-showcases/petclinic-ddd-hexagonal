package io.github.dmitriirussu.petclinic.vet.port.in;

import io.github.dmitriirussu.petclinic.vet.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;

import java.util.List;

public interface VetQueryPort {
    Vet findById(VetId id);
    List<Vet> findAll(int page, int pageSize);
    int countAll();
}

