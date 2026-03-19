package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddVisitRequest(
        @NotNull LocalDate date,
        @NotBlank String description
) {}
