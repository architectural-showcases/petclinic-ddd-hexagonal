package io.github.dmitriirussu.petclinic.owner.core.domain.visit;

import io.github.dmitriirussu.petclinic.kernel.idgenerator.IdGenerator;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit.VisitDate;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit.VisitDescription;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class VisitFactory {

    private final IdGenerator idGenerator;

    public VisitFactory(IdGenerator idGenerator) {
        this.idGenerator = Objects.requireNonNull(idGenerator);
    }

    public Visit create(VisitDate date, VisitDescription description) {
        return new Visit(
                VisitId.of(idGenerator.nextId()),
                date,
                description
        );
    }

    public Visit restore(VisitId id, LocalDate date, VisitDescription description) {
        return new Visit(
                id,
                VisitDate.restore(date),
                description
        );
    }
}