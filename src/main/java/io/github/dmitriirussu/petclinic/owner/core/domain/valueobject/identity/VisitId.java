package io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;

public final class VisitId extends SingleValueObject<String> {
    private VisitId(String value) { super(requireNonBlank(value, "VisitId")); }
    public static VisitId of(String value) { return new VisitId(value); }
}
