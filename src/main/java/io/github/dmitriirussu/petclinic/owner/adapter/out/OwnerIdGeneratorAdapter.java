package io.github.dmitriirussu.petclinic.owner.adapter.out;

import io.github.dmitriirussu.petclinic.kernel.idgenerator.IdGenerator;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.core.port.out.OwnerIdGeneratorPort;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OwnerIdGeneratorAdapter implements OwnerIdGeneratorPort {

    private final IdGenerator idGenerator;

    public OwnerIdGeneratorAdapter(IdGenerator idGenerator) {
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    @Override public OwnerId nextOwnerId() {
        return OwnerId.of(idGenerator.nextId());
    }
    @Override public PetId   nextPetId()   {
        return PetId.of(idGenerator.nextId());
    }
    @Override public VisitId nextVisitId() {
        return VisitId.of(idGenerator.nextId());
    }
}
