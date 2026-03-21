package io.github.dmitriirussu.petclinic.owner.core.port.in;

import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.Address;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.OwnerName;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.PhoneNumber;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.BirthDate;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetName;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit.VisitDate;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit.VisitDescription;

public interface OwnerCommandPort {

    OwnerId createOwner(OwnerName name, Address address, PhoneNumber telephone);
    void    updateOwner(OwnerId id, OwnerName name, Address address, PhoneNumber telephone);
    void    deleteOwner(OwnerId id);

    PetId addPet(OwnerId ownerId, PetName name, BirthDate birthDate, String typeName);
    void removePet(OwnerId ownerId, PetId petId);
    void updatePet(OwnerId ownerId, PetId petId, PetName name, BirthDate birthDate, String typeName);

    VisitId addVisit(OwnerId ownerId, PetId petId, VisitDate date, VisitDescription description);
    void removeVisit(OwnerId ownerId, PetId petId, VisitId visitId);
}
