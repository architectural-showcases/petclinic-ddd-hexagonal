package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.response;

import io.github.dmitriirussu.petclinic.owner.domain.visit.Visit;

import java.time.LocalDate;

public record VisitResponse(
        String    id,
        LocalDate date,
        String    description
) {
    public static VisitResponse from(Visit visit) {
        return new VisitResponse(
                visit.getId().value(),
                visit.getDate(),
                visit.getDescription()
        );
    }
}

