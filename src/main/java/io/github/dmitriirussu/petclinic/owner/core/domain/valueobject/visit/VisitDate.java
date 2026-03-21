package io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;
import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.Objects;

public final class VisitDate extends SingleValueObject<LocalDate> {

    private VisitDate(LocalDate value) {
        super(value);
    }

    public static VisitDate of(LocalDate value) {
        Objects.requireNonNull(value, "VisitDate required");
        if (value.isBefore(LocalDate.now()))
            throw new DomainValidationException("Visit date cannot be in the past");
        return new VisitDate(value);
    }

    public static VisitDate of(int year, int month, int day) {
        return of(LocalDate.of(year, month, day));
    }

    public static VisitDate restore(LocalDate value) {
        Objects.requireNonNull(value, "VisitDate required");
        return new VisitDate(value);
    }
}