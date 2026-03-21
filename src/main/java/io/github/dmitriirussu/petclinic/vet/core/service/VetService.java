package io.github.dmitriirussu.petclinic.vet.core.service;

import io.github.dmitriirussu.petclinic.kernel.exception.EntityNotFoundException;
import io.github.dmitriirussu.petclinic.kernel.pagination.Page;
import io.github.dmitriirussu.petclinic.vet.core.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.port.in.VetQueryPort;
import io.github.dmitriirussu.petclinic.vet.core.port.out.VetReadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class VetService implements VetQueryPort {

    private final VetReadRepository read;

    public VetService(VetReadRepository read) {
        this.read = Objects.requireNonNull(read);
    }

    @Override
    public Vet findById(VetId id) {
        return read.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vet not found: " + id));
    }

    @Override
    public Page<Vet> findAll(int page, int pageSize) {
        return read.findAll(page, pageSize);
    }
}