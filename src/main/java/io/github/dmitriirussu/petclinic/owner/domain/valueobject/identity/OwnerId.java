package io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;

public final class OwnerId extends SingleValueObject<String> {
    private OwnerId(String value) { super(requireNonBlank(value, "OwnerId")); }
    public static OwnerId of(String value) { return new OwnerId(value); }
}
