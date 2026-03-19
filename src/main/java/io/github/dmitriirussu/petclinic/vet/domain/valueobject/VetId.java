package io.github.dmitriirussu.petclinic.vet.domain.valueobject;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;

public final class VetId extends SingleValueObject<String> {
    private VetId(String value) { super(requireNonBlank(value, "VetId")); }
    public static VetId of(String value) { return new VetId(value); }
}
