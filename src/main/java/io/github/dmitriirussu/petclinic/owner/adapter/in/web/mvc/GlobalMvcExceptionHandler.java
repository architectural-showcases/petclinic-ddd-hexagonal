package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc;

import io.github.dmitriirussu.petclinic.kernel.exception.DomainValidationException;
import io.github.dmitriirussu.petclinic.kernel.exception.EntityNotFoundException;
import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.PetFormDto;
import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.VisitFormDto;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerQueryPort;
import io.github.dmitriirussu.petclinic.owner.port.out.PetTypeCatalog;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
/*@ControllerAdvice(basePackages = "io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc")*/
public class GlobalMvcExceptionHandler {

    private final OwnerQueryPort query;
    private final PetTypeCatalog petTypeCatalog;

    public GlobalMvcExceptionHandler(OwnerQueryPort query, PetTypeCatalog petTypeCatalog) {
        this.query          = query;
        this.petTypeCatalog = petTypeCatalog;
    }

    @ExceptionHandler(DomainValidationException.class)
    public String handleValidation(
            DomainValidationException ex,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute("error", ex.getMessage());

        // визит
        if (request.getAttribute("visitForm") instanceof VisitFormDto visitForm) {
            String ownerId = (String) request.getAttribute("ownerId");
            String petId   = (String) request.getAttribute("petId");
            Owner owner    = query.findById(OwnerId.of(ownerId));
            Pet pet = owner.getPets().stream()
                    .filter(p -> p.getId().value().equals(petId))
                    .findFirst()
                    .orElseThrow();
            model.addAttribute("visitForm", visitForm);
            model.addAttribute("owner", owner);
            model.addAttribute("pet", pet);
            return "owner/form/visit-form";
        }

        // питомец
        if (request.getAttribute("petForm") instanceof PetFormDto petForm) {
            String ownerId = (String) request.getAttribute("ownerId");
            String petId   = (String) request.getAttribute("petId");
            Owner owner    = query.findById(OwnerId.of(ownerId));
            model.addAttribute("petForm", petForm);
            model.addAttribute("owner", owner);
            model.addAttribute("petId", petId);
            model.addAttribute("petTypes", petTypeCatalog.getAllTypes());
            return "owner/form/pet-form";
        }

        // владелец
        model.addAttribute("ownerForm", request.getAttribute("ownerForm"));
        model.addAttribute("ownerId", request.getAttribute("ownerId"));
        return "owner/form/create-edit-form";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("message", "");
        return "owner/form/main-page-search";
    }
}