package io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.command;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.request.*;
import io.github.dmitriirussu.petclinic.owner.adapter.in.web.rest.response.CreatedResponse;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.Address;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.OwnerName;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.personal.PhoneNumber;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.BirthDate;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.pet.PetName;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit.VisitDate;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.visit.VisitDescription;
import io.github.dmitriirussu.petclinic.owner.core.port.in.OwnerCommandPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/owners")
public class RestOwnerCommandController {

    private final OwnerCommandPort command;

    public RestOwnerCommandController(OwnerCommandPort command) {
        this.command = Objects.requireNonNull(command);
    }

    // ─── Owner ───────────────────────────────────────────────

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse create(@Valid @RequestBody CreateOwnerRequest req) {
        OwnerId id = command.createOwner(
                OwnerName.of(req.firstName(), req.lastName()),
                Address.of(req.street(), req.city()),
                PhoneNumber.of(req.telephone())
        );
        return new CreatedResponse(id.value());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id,
                       @Valid @RequestBody UpdateOwnerRequest req) {
        command.updateOwner(
                OwnerId.of(id),
                OwnerName.of(req.firstName(), req.lastName()),
                Address.of(req.street(), req.city()),
                PhoneNumber.of(req.telephone())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        command.deleteOwner(OwnerId.of(id));
    }

    // ─── Pet ─────────────────────────────────────────────────

    @PostMapping("/{ownerId}/pets")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse addPet(@PathVariable String ownerId,
                       @Valid @RequestBody AddPetRequest req) {
        PetId id = command.addPet(
                OwnerId.of(ownerId),
                PetName.of(req.name()),
                BirthDate.of(req.birthDate().getYear(),
                        req.birthDate().getMonthValue(),
                        req.birthDate().getDayOfMonth()),
                req.type()
        );
        return new CreatedResponse(id.value());
    }

    @PutMapping("/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePet(@PathVariable String ownerId,
                          @PathVariable String petId,
                          @Valid @RequestBody UpdatePetRequest req) {
        command.updatePet(
                OwnerId.of(ownerId),
                PetId.of(petId),
                PetName.of(req.name()),
                BirthDate.of(req.birthDate().getYear(),
                        req.birthDate().getMonthValue(),
                        req.birthDate().getDayOfMonth()),
                req.type()
        );
    }

    @DeleteMapping("/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePet(@PathVariable String ownerId,
                          @PathVariable String petId) {
        command.removePet(OwnerId.of(ownerId), PetId.of(petId));
    }

    // ─── Visit ───────────────────────────────────────────────

    @PostMapping("/{ownerId}/pets/{petId}/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse addVisit(@PathVariable String ownerId,
                                    @PathVariable String petId,
                                    @Valid @RequestBody AddVisitRequest req) {
        VisitId id = command.addVisit(
                OwnerId.of(ownerId),
                PetId.of(petId),
                VisitDate.of(req.date()),
                VisitDescription.of(req.description())
        );
        return new CreatedResponse(id.value());
    }

    @DeleteMapping("/{ownerId}/pets/{petId}/visits/{visitId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeVisit(@PathVariable String ownerId,
                            @PathVariable String petId,
                            @PathVariable String visitId) {
        command.removeVisit(
                OwnerId.of(ownerId),
                PetId.of(petId),
                VisitId.of(visitId)
        );
    }
}
