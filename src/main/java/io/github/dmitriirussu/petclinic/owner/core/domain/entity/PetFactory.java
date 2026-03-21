package io.github.dmitriirussu.petclinic.owner.core.domain.entity;

import io.github.dmitriirussu.petclinic.kernel.idgenerator.IdGenerator;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.BirthDate;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetName;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetType;
import io.github.dmitriirussu.petclinic.owner.core.port.out.PetTypeCatalog;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PetFactory {

    private final IdGenerator idGenerator;
    private final PetTypeCatalog catalog;

    public PetFactory(IdGenerator idGenerator, PetTypeCatalog catalog) {
        this.idGenerator = Objects.requireNonNull(idGenerator);
        this.catalog     = Objects.requireNonNull(catalog);
    }

    public Pet create(PetName name, BirthDate birthDate, String typeName) {
        return new Pet(
                PetId.of(idGenerator.nextId()),
                name,
                birthDate,
                catalog.findByName(typeName)
        );
    }

    public Pet restore(PetId id, PetName name, BirthDate birthDate, PetType type) {
        return new Pet(id, name, birthDate, type);
    }
}
