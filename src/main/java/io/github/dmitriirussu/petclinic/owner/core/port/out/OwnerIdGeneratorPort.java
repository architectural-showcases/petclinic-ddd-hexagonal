package io.github.dmitriirussu.petclinic.owner.core.port.out;

import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.VisitId;

public interface OwnerIdGeneratorPort {
    OwnerId nextOwnerId();
    PetId nextPetId();
    VisitId nextVisitId();
}
