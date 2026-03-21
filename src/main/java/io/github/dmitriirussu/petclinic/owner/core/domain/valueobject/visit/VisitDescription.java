package io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;
import io.github.dmitriirussu.petclinic.kernel.text.Text;
import io.github.dmitriirussu.petclinic.kernel.text.TextPolicy;

public final class VisitDescription extends SingleValueObject<Text> {

    private VisitDescription(Text value) { super(value); }

    public static VisitDescription of(String raw) {
        return new VisitDescription(Text.of(raw, TextPolicy.description()));
    }

    public String asString() { return value.value(); }

    @Override public String toString() { return value.value(); }
}
