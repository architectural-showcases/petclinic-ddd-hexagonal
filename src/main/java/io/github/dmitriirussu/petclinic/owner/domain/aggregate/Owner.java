package io.github.dmitriirussu.petclinic.owner.domain.aggregate;

import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;
import io.github.dmitriirussu.petclinic.kernel.exception.EntityNotFoundException;
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
import io.github.dmitriirussu.petclinic.owner.domain.visit.Visit;

import java.util.*;

public class Owner {

    private final OwnerId id;
    private OwnerName name;
    private Address address;
    private PhoneNumber telephone;

    private final Map<PetId, Pet> pets = new LinkedHashMap<>();

    Owner(OwnerId id, OwnerName name, Address address, PhoneNumber telephone) {
        this.id        = Objects.requireNonNull(id,        "id required");
        this.name      = Objects.requireNonNull(name,      "name required");
        this.address   = Objects.requireNonNull(address,   "address required");
        this.telephone = Objects.requireNonNull(telephone, "telephone required");
    }

    public OwnerId    getId()        { return id; }
    public OwnerName getName()      { return name; }
    public Address    getAddress()   { return address; }
    public PhoneNumber getPhone()    { return telephone; }

    public String getFirstName()  { return name.firstName(); }
    public String getLastName()   { return name.lastName(); }
    public String getStreet()     { return address.street(); }
    public String getCity()       { return address.city(); }
    public String getTelephone()  { return telephone.value(); }

    public Collection<Pet> getPets() {
        return Collections.unmodifiableCollection(pets.values());
    }

    public void update(OwnerName name, Address address, PhoneNumber telephone) {
        this.name      = Objects.requireNonNull(name,      "name required");
        this.address   = Objects.requireNonNull(address,   "address required");
        this.telephone = Objects.requireNonNull(telephone, "telephone required");
    }

    public void addPet(Pet pet) {
        Objects.requireNonNull(pet, "pet required");
        boolean duplicate = pets.values().stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(pet.getName())
                        && p.getBirthDate().equals(pet.getBirthDate())
                        && p.getType().equals(pet.getType()));
        if (duplicate)
            throw new DomainValidationException("Pet already exists: " + pet.getName());
        pets.put(pet.getId(), pet);
    }

    public void removePet(PetId petId) {
        if (pets.remove(petId) == null)
            throw new EntityNotFoundException("Pet not found: " + petId);
    }

    public void updatePet(PetId petId, PetName name, BirthDate birthDate, PetType type) {
        Pet pet = Optional.ofNullable(pets.get(petId))
                .orElseThrow(() -> new EntityNotFoundException("Pet not found: " + petId));
        pet.update(name, birthDate, type);
    }

    public void addVisitToPet(PetId petId, Visit visit) {
        Pet pet = Optional
                .ofNullable(pets.get(petId))
                .orElseThrow(
                        () -> new EntityNotFoundException("Pet not found: " + petId)
                );
        pet.addVisit(visit);
    }

    public void removeVisitFromPet(PetId petId, VisitId visitId) {
        Pet pet = Optional
                .ofNullable(pets.get(petId))
                .orElseThrow(
                        () -> new EntityNotFoundException("Pet not found: " + petId)
                );
        pet.removeVisit(visitId);
    }

    @Override public boolean equals(Object o) {
        return o instanceof Owner other && id.equals(other.id);
    }
    @Override public int hashCode() { return id.hashCode(); }

    @Override public String toString() {
        return "Owner{id=" + id + ", name=" + name + "}";
    }
}
