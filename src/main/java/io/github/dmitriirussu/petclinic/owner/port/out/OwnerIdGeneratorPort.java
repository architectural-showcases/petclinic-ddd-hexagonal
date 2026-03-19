package io.github.dmitriirussu.petclinic.owner.port.out;

import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.VisitId;

public interface OwnerIdGeneratorPort {
    OwnerId nextOwnerId();
    PetId nextPetId();
    VisitId nextVisitId();
}
