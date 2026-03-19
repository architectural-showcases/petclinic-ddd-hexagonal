package io.github.dmitriirussu.petclinic.vet.adapter.out;

import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.port.out.VetIdGeneratorPort;
import io.github.dmitriirussu.petclinic.kernel.idgenerator.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VetIdGeneratorAdapter implements VetIdGeneratorPort {

    private final IdGenerator idGenerator;

    public VetIdGeneratorAdapter(IdGenerator idGenerator) {
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    @Override
    public VetId nextVetId() {
        return VetId.of(idGenerator.nextId());
    }
}
