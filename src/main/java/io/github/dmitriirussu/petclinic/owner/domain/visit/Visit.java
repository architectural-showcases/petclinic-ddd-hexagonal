package io.github.dmitriirussu.petclinic.owner.domain.visit;

import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDescription;

import java.time.LocalDate;
import java.util.Objects;

public class Visit {

    private final VisitId id;
    private final VisitDate date;
    private final VisitDescription description;

    public Visit(VisitId id, VisitDate date, VisitDescription description) {
        this.id = Objects.requireNonNull(id, "id required");
        this.date = Objects.requireNonNull(date, "date required");
        this.description = Objects.requireNonNull(description, "description required");
    }

    public VisitId getId() { return id; }
    public LocalDate getDate() { return date.value(); }
    public String getDescription() { return description.asString(); }
    public VisitDate getVisitDate() { return date; }
    public VisitDescription getVisitDescription() { return description; }

    @Override public boolean equals(Object o) {
        return o instanceof Visit other && id.equals(other.id);
    }
    @Override public int hashCode() { return id.hashCode(); }

    @Override public String toString() { return date + ": " + description; }
}
