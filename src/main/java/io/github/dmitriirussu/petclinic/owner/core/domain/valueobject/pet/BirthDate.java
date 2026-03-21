package io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;
import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;

import java.time.LocalDate;
import java.util.Objects;

public final class BirthDate extends SingleValueObject<LocalDate> {

    private BirthDate(LocalDate value) {
        super(validated(value));
    }

    public static BirthDate of(LocalDate value) { return new BirthDate(value); }
    public static BirthDate of(int year, int month, int day) {
        return new BirthDate(LocalDate.of(year, month, day));
    }

    private static LocalDate validated(LocalDate value) {
        Objects.requireNonNull(value, "BirthDate required");
        if (value.isAfter(LocalDate.now()))
            throw new DomainValidationException("Birth date cannot be in the future");
        return value;
    }
}
