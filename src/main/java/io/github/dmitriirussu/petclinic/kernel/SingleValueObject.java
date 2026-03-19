package io.github.dmitriirussu.petclinic.kernel;

import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;

import java.util.Objects;

public abstract class SingleValueObject<T> {

    protected final T value;

    protected SingleValueObject(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public T value() { return value; }

    protected static String requireNonBlank(String value, String field) {
        String t = Objects.requireNonNull(value, field + " required").trim();
        if (t.isBlank()) throw new DomainValidationException(field + " cannot be blank");
        return t;
    }

    @Override public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return value.equals(((SingleValueObject<?>) o).value);
    }
    @Override public final int hashCode() { return value.hashCode(); }
    @Override public String toString()    { return value.toString(); }
}
