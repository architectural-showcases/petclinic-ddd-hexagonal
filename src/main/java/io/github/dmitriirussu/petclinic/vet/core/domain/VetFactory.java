package io.github.dmitriirussu.petclinic.vet.core.domain;

import io.github.dmitriirussu.petclinic.kernel.idgenerator.IdGenerator;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetName;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VetFactory {

    private final IdGenerator idGenerator;

    public VetFactory(IdGenerator idGenerator) {
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    public Vet create(VetId id, VetName name) {
        return new Vet(id, name);
    }

    public Vet restore(VetId id, VetName name) {
        return new Vet(id, name);
    }
}
