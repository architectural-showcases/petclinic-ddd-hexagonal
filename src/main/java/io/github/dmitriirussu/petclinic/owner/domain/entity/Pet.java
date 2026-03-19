package io.github.dmitriirussu.petclinic.owner.domain.entity;

import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;
import io.github.dmitriirussu.petclinic.kernel.exception.EntityNotFoundException;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.BirthDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetName;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetType;
import io.github.dmitriirussu.petclinic.owner.domain.visit.Visit;

import java.time.LocalDate;
import java.util.*;

public class Pet {

    private final PetId id;
    private PetName name;
    private BirthDate birthDate;
    private PetType type;
    private final Map<VisitId, Visit> visits = new LinkedHashMap<>();

    public Pet(PetId id, PetName name, BirthDate birthDate, PetType type) {
        this.id        = Objects.requireNonNull(id,        "id required");
        this.name      = Objects.requireNonNull(name,      "name required");
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate required");
        this.type      = Objects.requireNonNull(type,      "type required");
    }

    public PetId      getId()        { return id; }
    public String     getName()      { return name.asString(); }
    public LocalDate  getBirthDate() { return birthDate.value(); }
    public String getType() { return type.getName(); }
    public PetName    getPetName()   { return name; }
    public BirthDate  getBirth()     { return birthDate; }

    public Collection<Visit> getVisits() {
        return Collections.unmodifiableCollection(visits.values());
    }

    public void update(PetName name, BirthDate birthDate, PetType type) {
        this.name      = Objects.requireNonNull(name,      "name required");
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate required");
        this.type      = Objects.requireNonNull(type,      "type required");
    }

    public void addVisit(Visit visit) {
        Objects.requireNonNull(visit, "visit required");
        boolean duplicate = visits.values().stream()
                .anyMatch(v -> v.getVisitDate().equals(visit.getVisitDate())
                        && v.getVisitDescription().equals(visit.getVisitDescription()));
        if (duplicate)
            throw new DomainValidationException(
                    "Visit already exists: " + visit.getDate() + " - " + visit.getDescription());
        visits.put(visit.getId(), visit);
    }

    public void removeVisit(VisitId visitId) {
        if (visits.remove(visitId) == null)
            throw new EntityNotFoundException("Visit not found: " + visitId);
    }

    @Override public boolean equals(Object o) {
        return o instanceof Pet other && id.equals(other.id);
    }
    @Override public int hashCode() { return id.hashCode(); }

    @Override public String toString() { return name.asString(); }
}
