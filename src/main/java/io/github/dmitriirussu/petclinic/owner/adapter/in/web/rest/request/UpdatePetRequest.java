package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdatePetRequest(
        @NotBlank String name,
        @NotNull LocalDate birthDate,
        @NotBlank String type
) {}