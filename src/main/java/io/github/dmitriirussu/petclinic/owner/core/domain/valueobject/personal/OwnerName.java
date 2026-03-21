package io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal;

import io.github.dmitriirussu.petclinic.kernel.text.Text;
import io.github.dmitriirussu.petclinic.kernel.text.TextPolicy;

import java.util.Objects;

public final class OwnerName {

    private final Text firstName;
    private final Text lastName;

    private OwnerName(String firstName, String lastName) {
        this.firstName = Text.of(firstName, TextPolicy.personName());
        this.lastName  = Text.of(lastName,  TextPolicy.personName());
    }

    public static OwnerName of(String firstName, String lastName) {
        return new OwnerName(firstName, lastName);
    }

    public String firstName() { return firstName.value(); }
    public String lastName()  { return lastName.value(); }
    public String fullName()  { return firstName + " " + lastName; }

    @Override public boolean equals(Object o) {
        return o instanceof OwnerName n
                && firstName.equals(n.firstName)
                && lastName.equals(n.lastName);
    }
    @Override public int hashCode() { return Objects.hash(firstName, lastName); }
    @Override public String toString() { return fullName(); }
}
