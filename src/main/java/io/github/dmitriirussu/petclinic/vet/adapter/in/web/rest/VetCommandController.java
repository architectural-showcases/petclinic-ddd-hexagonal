/*package io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest;

import io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.request.CreateVetRequest;
import io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.request.SpecialtyRequest;
import io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.request.UpdateVetRequest;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetId;
import io.github.dmitriirussu.petclinic.vet.domain.valueobject.VetName;
import io.github.dmitriirussu.petclinic.vet.adapter.in.web.rest.response.CreatedResponse;
import io.github.dmitriirussu.petclinic.vet.port.in.VetCommandPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/vets")
public class VetCommandController {

    private final VetCommandPort command;

    public VetCommandController(VetCommandPort command) {
        this.command = Objects.requireNonNull(command);
    }

    // ─── Vet ─────────────────────────────────────────────────

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse create(@Valid @RequestBody CreateVetRequest req) {
        VetId id = command.createVet(VetName.of(req.firstName(), req.lastName()));
        return new CreatedResponse(id.value());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id,
                       @Valid @RequestBody UpdateVetRequest req) {
        command.updateVet(VetId.of(id), VetName.of(req.firstName(), req.lastName()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        command.deleteVet(VetId.of(id));
    }

    // ─── Specialty ───────────────────────────────────────────

    @PostMapping("/{id}/specialties")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSpecialty(@PathVariable String id,
                             @Valid @RequestBody SpecialtyRequest req) {
        command.addSpecialty(VetId.of(id), req.name());
    }

    @DeleteMapping("/{id}/specialties/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSpecialty(@PathVariable String id,
                                @PathVariable String name) {
        command.removeSpecialty(VetId.of(id), name);
    }
}*/
