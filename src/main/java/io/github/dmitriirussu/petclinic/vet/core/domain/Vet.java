package io.github.dmitriirussu.petclinic.vet.core.domain;

import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.Specialty;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.core.domain.valueobject.VetName;

import java.util.*;

public class Vet {

    private final VetId id;
    private VetName name;
    private final Map<String, Specialty> specialties = new LinkedHashMap<>();

    public Vet(VetId id, VetName name) {
        this.id   = Objects.requireNonNull(id,   "id required");
        this.name = Objects.requireNonNull(name, "name required");
    }

    public VetId   getId()        { return id; }
    public String  getFirstName() { return name.firstName(); }
    public String  getLastName()  { return name.lastName(); }
    public VetName getName()      { return name; }

    public Collection<Specialty> getSpecialties() {
        return Collections.unmodifiableCollection(specialties.values());
    }

    public int getSpecialtyCount() { return specialties.size(); }

    public void update(VetName name) {
        this.name = Objects.requireNonNull(name, "name required");
    }

    public void addSpecialty(Specialty specialty) {
        Objects.requireNonNull(specialty, "specialty required");
        String key = specialty.value().canonical();
        if (specialties.containsKey(key))
            throw new IllegalStateException("Specialty already exists: " + specialty);
        specialties.put(key, specialty);
    }

    public void removeSpecialty(Specialty specialty) {
        Objects.requireNonNull(specialty, "specialty required");
        if (specialties.remove(specialty.value().canonical()) == null)
            throw new NoSuchElementException("Specialty not found: " + specialty);
    }

    public boolean hasSpecialty(Specialty specialty) {
        return specialties.containsKey(specialty.value().canonical());
    }

    @Override public boolean equals(Object o) {
        return o instanceof Vet other && id.equals(other.id);
    }
    @Override public int hashCode() { return id.hashCode(); }
    @Override public String toString() {
        return "Vet{id=" + id + ", name=" + name + "}";
    }
}

