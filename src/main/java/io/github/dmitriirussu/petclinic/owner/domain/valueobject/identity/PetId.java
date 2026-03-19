package io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;

public final class PetId extends SingleValueObject<String> {
    private PetId(String value) { super(requireNonBlank(value, "PetId")); }
    public static PetId of(String value) { return new PetId(value); }
}
