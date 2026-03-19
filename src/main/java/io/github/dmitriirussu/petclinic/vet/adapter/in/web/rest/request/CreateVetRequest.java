package io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.request;

import jakarta.validation.constraints.NotBlank;

public record CreateVetRequest(
        @NotBlank String firstName,
        @NotBlank String lastName
) {}
