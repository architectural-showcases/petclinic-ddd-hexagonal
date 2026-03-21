package io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal;

import io.github.dmitriirussu.petclinic.kernel.text.Text;
import io.github.dmitriirussu.petclinic.kernel.text.TextPolicy;

import java.util.Objects;

public final class Address {

    private final Text street;
    private final Text city;

    private Address(String street, String city) {
        this.street = Text.of(street, TextPolicy.address());
        this.city   = Text.of(city,   TextPolicy.city());
    }

    public static Address of(String street, String city) {
        return new Address(street, city);
    }

    public String street() { return street.value(); }
    public String city()   { return city.value(); }

    @Override public boolean equals(Object o) {
        return o instanceof Address a
                && street.equals(a.street)
                && city.equals(a.city);
    }
    @Override public int hashCode() { return Objects.hash(street, city); }
    @Override public String toString() { return street + ", " + city; }
}
