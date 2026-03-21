package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.response;

import io.github.dmitriirussu.petclinic.owner.core.domain.entity.Pet;

import java.time.LocalDate;
import java.util.List;

public record PetResponse(
        String            id,
        String            name,
        LocalDate birthDate,
        String            type,
        List<VisitResponse> visits
) {
    public static PetResponse from(Pet pet) {
        return new PetResponse(
                pet.getId().value(),
                pet.getName(),
                pet.getBirthDate(),
                pet.getType(),
                pet.getVisits().stream().map(VisitResponse::from).toList()
        );
    }
}
