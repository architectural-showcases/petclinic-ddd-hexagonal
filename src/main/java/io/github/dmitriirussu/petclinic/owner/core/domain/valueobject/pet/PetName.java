package io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;
import io.github.dmitriirussu.petclinic.kernel.text.Text;
import io.github.dmitriirussu.petclinic.kernel.text.TextPolicy;

public final class PetName extends SingleValueObject<Text> {

    private PetName(Text value) { super(value); }

    public static PetName of(String raw) {
        return new PetName(Text.of(raw, TextPolicy.petName()));
    }

    public String asString() { return value.value(); }

    @Override public String toString() { return value.value(); }
}
