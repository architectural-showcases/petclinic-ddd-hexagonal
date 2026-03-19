package io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet;

import io.github.dmitriirussu.petclinic.kernel.SingleValueObject;
import io.github.dmitriirussu.petclinic.kernel.text.Text;
import io.github.dmitriirussu.petclinic.kernel.text.TextPolicy;

public final class PetType extends SingleValueObject<Text> {

    private PetType(Text value) { super(value); }

    public static PetType of(String raw) {
        return new PetType(Text.of(raw, TextPolicy.petName()));
    }

    public String getName() { return value.value(); }

    @Override public String toString() { return value.value(); }
}
