package io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.response;

import io.github.dmitriirussu.petclinic.vet.domain.Vet;

import java.util.List;

public record VetResponse(
        String                  id,
        String                  firstName,
        String                  lastName,
        List<SpecialtyResponse> specialties
) {
    public static VetResponse from(Vet vet) {
        return new VetResponse(
                vet.getId().value(),
                vet.getFirstName(),
                vet.getLastName(),
                vet.getSpecialties().stream().map(SpecialtyResponse::from).toList()
        );
    }
}

