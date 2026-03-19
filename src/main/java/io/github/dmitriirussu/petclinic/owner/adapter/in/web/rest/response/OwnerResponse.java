package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.response;

import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;

import java.util.List;

public record OwnerResponse(
        String           id,
        String           firstName,
        String           lastName,
        String           street,
        String           city,
        String           telephone,
        List<PetResponse> pets
) {
    public static OwnerResponse from(Owner owner) {
        return new OwnerResponse(
                owner.getId().value(),
                owner.getFirstName(),
                owner.getLastName(),
                owner.getStreet(),
                owner.getCity(),
                owner.getTelephone(),
                owner.getPets().stream().map(PetResponse::from).toList()
        );
    }
}
