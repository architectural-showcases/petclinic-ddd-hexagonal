package io.github.dmitriirussu.petclinic.owner.adapter.in;

import io.github.dmitriirussu.petclinic.kernel.exception.EntityNotFoundException;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.OwnerFactory;
import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.Address;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.OwnerName;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.PhoneNumber;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.BirthDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetName;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetType;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDescription;
import io.github.dmitriirussu.petclinic.owner.domain.visit.Visit;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerCommandPort;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerQueryPort;
import io.github.dmitriirussu.petclinic.owner.port.out.OwnerIdGeneratorPort;
import io.github.dmitriirussu.petclinic.owner.port.out.OwnerReadRepository;
import io.github.dmitriirussu.petclinic.owner.port.out.OwnerWriteRepository;
import io.github.dmitriirussu.petclinic.owner.port.out.PetTypeCatalog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OwnerService implements OwnerCommandPort, OwnerQueryPort {

    private final OwnerWriteRepository write;
    private final OwnerReadRepository read;
    private final PetTypeCatalog catalog;
    private final OwnerFactory factory;
    private final OwnerIdGeneratorPort idGenerator;

    public OwnerService(
            OwnerWriteRepository write,
            OwnerReadRepository  read,
            PetTypeCatalog       catalog,
            OwnerFactory         factory,
            OwnerIdGeneratorPort idGenerator
    ) {
        this.write       = Objects.requireNonNull(write);
        this.read        = Objects.requireNonNull(read);
        this.catalog     = Objects.requireNonNull(catalog);
        this.factory     = Objects.requireNonNull(factory);
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    // ─── OwnerCommandPort ────────────────────────────────────

    @Override
    public OwnerId createOwner(OwnerName name, Address address, PhoneNumber telephone) {
        Owner owner = factory.create(name, address, telephone);
        write.save(owner);
        return owner.getId();
    }

    @Override
    public void updateOwner(OwnerId id, OwnerName name,
                            Address address, PhoneNumber telephone) {
        Objects.requireNonNull(id,        "id required");
        Owner owner = findById(id);
        owner.update(name, address, telephone);
        write.update(id, owner);
    }

    @Override
    public void deleteOwner(OwnerId id) {
        Objects.requireNonNull(id, "id required"); // ✅ уже есть
        write.delete(id);
    }

    @Override
    public PetId addPet(OwnerId ownerId, PetName name, BirthDate birthDate, String typeName) {
        Objects.requireNonNull(ownerId,   "ownerId required");
        PetId   petId = idGenerator.nextPetId();
        Owner   owner = findById(ownerId);
        PetType type  = catalog.findByName(typeName);
        owner.addPet(new Pet(petId, name, birthDate, type));
        write.update(ownerId, owner);
        return petId;
    }

    @Override
    public void removePet(OwnerId ownerId, PetId petId) {
        Objects.requireNonNull(ownerId, "ownerId required");
        Objects.requireNonNull(petId,   "petId required");
        Owner owner = findById(ownerId);
        owner.removePet(petId);
        write.update(ownerId, owner);
    }


    @Override
    public void updatePet(OwnerId ownerId, PetId petId,
                          PetName name, BirthDate birthDate, String typeName) {
        Objects.requireNonNull(ownerId,   "ownerId required");
        Objects.requireNonNull(petId,     "petId required");
        Owner   owner = findById(ownerId);
        PetType type  = catalog.findByName(typeName);
        owner.updatePet(petId, name, birthDate, type);
        write.update(ownerId, owner);
    }

    @Override
    public VisitId addVisit(OwnerId ownerId, PetId petId,
                            VisitDate date, VisitDescription description) {
        Objects.requireNonNull(ownerId,     "ownerId required");     // ← добавить
        Objects.requireNonNull(petId,       "petId required");       // ← добавить
        VisitId visitId = idGenerator.nextVisitId();
        Visit   visit   = new Visit(visitId, date, description);
        Owner   owner   = findById(ownerId);
        owner.addVisitToPet(petId, visit);
        write.update(ownerId, owner);
        return visitId;
    }


    @Override
    public void removeVisit(OwnerId ownerId, PetId petId, VisitId visitId) {
        Objects.requireNonNull(ownerId,  "ownerId required");  // ← добавить
        Objects.requireNonNull(petId,    "petId required");    // ← добавить
        Objects.requireNonNull(visitId,  "visitId required");  // ← добавить
        Owner owner = findById(ownerId);
        owner.removeVisitFromPet(petId, visitId);
        write.update(ownerId, owner);
    }


    // ─── OwnerQueryPort ──────────────────────────────────────

    @Override
    public Owner findById(OwnerId id) {
        return read.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found: " + id));
    }

    @Override
    public List<Owner> findPaginated(String lastName, int page, int pageSize) {
        if (lastName == null || lastName.isBlank()) {
            return read.findAll(page, pageSize);
        }
        return read.findByLastName(lastName, page, pageSize);
    }

    @Override
    public int countByLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            return read.countAll();
        }
        return read.countByLastName(lastName);
    }
}
