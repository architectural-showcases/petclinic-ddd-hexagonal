package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.command;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.VisitFormDto;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.VisitId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.visit.VisitDescription;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerCommandPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/owners")
public class MvcVisitCommandController {

    private final OwnerCommandPort command;

    public MvcVisitCommandController(OwnerCommandPort command) {
        this.command = command;
    }

    @PostMapping("/{ownerId}/pets/{petId}/visits/new")
    public String addVisit(
            @PathVariable String ownerId,
            @PathVariable String petId,
            @ModelAttribute VisitFormDto visitForm,
            HttpServletRequest request,
            RedirectAttributes ra
    ) {
        request.setAttribute("visitForm", visitForm);
        request.setAttribute("ownerId", ownerId);
        request.setAttribute("petId", petId);
        command.addVisit(
                OwnerId.of(ownerId),
                PetId.of(petId),
                VisitDate.of(LocalDate.parse(visitForm.getDate())),
                VisitDescription.of(visitForm.getDescription())
        );
        ra.addFlashAttribute("message", "Visit added successfully");
        return "redirect:/owners/" + ownerId;
    }

    @PostMapping("/{ownerId}/pets/{petId}/visits/{visitId}/delete")
    public String deleteVisit(
            @PathVariable String ownerId,
            @PathVariable String petId,
            @PathVariable String visitId,
            RedirectAttributes ra
    ) {
        command.removeVisit(
                OwnerId.of(ownerId),
                PetId.of(petId),
                VisitId.of(visitId)
        );
        ra.addFlashAttribute("message", "Visit deleted successfully");
        return "redirect:/owners/" + ownerId;
    }
}
