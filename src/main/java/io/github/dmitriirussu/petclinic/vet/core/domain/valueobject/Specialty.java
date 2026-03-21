package io.github.dmitriirussu.petclinic.vet.core.domain.valueobject;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;
import io.github.dmitriirussu.petclinic.kernel.text.Text;
import io.github.dmitriirussu.petclinic.kernel.text.TextPolicy;

public final class Specialty extends SingleValueObject<Text> {

    private Specialty(Text text) { super(text); }

    public static Specialty of(String raw) {
        return new Specialty(Text.of(raw, TextPolicy.specialty()));
    }

    public String getName()  { return value.value(); }
    public String asString() { return value.value(); }

    @Override public String toString() { return value.value(); }
}
