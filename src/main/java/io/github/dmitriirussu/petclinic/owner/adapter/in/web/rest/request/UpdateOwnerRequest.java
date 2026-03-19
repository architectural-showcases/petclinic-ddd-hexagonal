package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateOwnerRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String street,
        @NotBlank String city,
        @NotBlank String telephone
) {}
