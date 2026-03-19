package io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.response;

import io.github.dmitriirussu.petclinic.vet.domain.valueobject.Specialty;

public record SpecialtyResponse(String name) {
    public static SpecialtyResponse from(Specialty specialty) {
        return new SpecialtyResponse(specialty.getName());
    }
}
