package io.github.dmitriirussu.petclinic.owner.core.service;

import io.github.dmitriirussu.petclinic.kernel.exception.EntityNotFoundException;
import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.OwnerFactory;
import io.github.dmitriirussu.petclinic.owner.core.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.core.domain.entity.PetFactory;
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
import io.github.dmitriirussu.petclinic.owner.core.domain.visit.Visit;
import io.github.dmitriirussu.petclinic.owner.core.domain.visit.VisitFactory;
import io.github.dmitriirussu.petclinic.owner.core.port.in.OwnerCommandPort;
import io.github.dmitriirussu.petclinic.owner.core.port.out.OwnerReadRepository;
import io.github.dmitriirussu.petclinic.owner.core.port.out.OwnerWriteRepository;
import io.github.dmitriirussu.petclinic.owner.core.port.out.PetTypeCatalog;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OwnerCommandService implements OwnerCommandPort {

    private final OwnerWriteRepository write;
    private final OwnerReadRepository  read;
    private final OwnerFactory         ownerFactory;
    private final PetFactory           petFactory;
    private final VisitFactory         visitFactory;
    private final PetTypeCatalog       catalog;

    public OwnerCommandService(
            OwnerWriteRepository write,
            OwnerReadRepository read,
            OwnerFactory         ownerFactory,
            PetFactory           petFactory,
            VisitFactory         visitFactory,
            PetTypeCatalog       catalog
    ) {
        this.write        = Objects.requireNonNull(write);
        this.read         = Objects.requireNonNull(read);
        this.ownerFactory = Objects.requireNonNull(ownerFactory);
        this.petFactory   = Objects.requireNonNull(petFactory);
        this.visitFactory = Objects.requireNonNull(visitFactory);
        this.catalog      = Objects.requireNonNull(catalog);
    }

    @Override
    public OwnerId createOwner(OwnerName name, Address address, PhoneNumber telephone) {
        Owner owner = ownerFactory.create(name, address, telephone);
        write.save(owner);
        return owner.getId();
    }

    @Override
    public void updateOwner(OwnerId id, OwnerName name, Address address, PhoneNumber telephone) {
        Owner owner = findById(id);
        owner.update(name, address, telephone);
        write.update(id, owner);
    }

    @Override
    public void deleteOwner(OwnerId id) {
        Objects.requireNonNull(id, "id required");
        write.delete(id);
    }

    @Override
    public PetId addPet(OwnerId ownerId, PetName name, BirthDate birthDate, String typeName) {
        Pet pet = petFactory.create(name, birthDate, typeName);
        Owner owner = findById(ownerId);
        owner.addPet(pet);
        write.update(ownerId, owner);
        return pet.getId();
    }

    @Override
    public void removePet(OwnerId ownerId, PetId petId) {
        Owner owner = findById(ownerId);
        owner.removePet(petId);
        write.update(ownerId, owner);
    }

    @Override
    public void updatePet(OwnerId ownerId, PetId petId, PetName name, BirthDate birthDate, String typeName) {
        Owner owner = findById(ownerId);
        owner.updatePet(petId, name, birthDate, catalog.findByName(typeName));
        write.update(ownerId, owner);
    }

    @Override
    public VisitId addVisit(OwnerId ownerId, PetId petId, VisitDate date, VisitDescription description) {
        Visit visit = visitFactory.create(date, description);
        Owner owner = findById(ownerId);
        owner.addVisitToPet(petId, visit);
        write.update(ownerId, owner);
        return visit.getId();
    }

    @Override
    public void removeVisit(OwnerId ownerId, PetId petId, VisitId visitId) {
        Owner owner = findById(ownerId);
        owner.removeVisitFromPet(petId, visitId);
        write.update(ownerId, owner);
    }

    private Owner findById(OwnerId id) {
        return read.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found: " + id));
    }
}