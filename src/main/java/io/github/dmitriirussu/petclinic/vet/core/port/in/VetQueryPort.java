package io.github.dmitriirussu.petclinic.vet.core.port.in;

import io.github.dmitriirussu.petclinic.kernel.pagination.Page;
import io.github.dmitriirussu.petclinic.vet.core.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;

import java.util.List;

public interface VetQueryPort {
    Vet findById(VetId id);
    Page<Vet> findAll(int page, int pageSize);
}

