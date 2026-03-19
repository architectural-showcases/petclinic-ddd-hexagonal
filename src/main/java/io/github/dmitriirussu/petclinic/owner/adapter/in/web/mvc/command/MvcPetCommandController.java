package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.command;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.PetFormDto;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.PetId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.BirthDate;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.pet.PetName;
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
public class MvcPetCommandController {

    private final OwnerCommandPort command;

    public MvcPetCommandController(OwnerCommandPort command) {
        this.command = command;
    }

    @PostMapping("/{ownerId}/pets/new")
    public String addPet(
            @PathVariable String ownerId,
            @ModelAttribute PetFormDto petForm,
            HttpServletRequest request,
            RedirectAttributes ra
    ) {
        request.setAttribute("petForm", petForm);
        request.setAttribute("ownerId", ownerId);
        request.setAttribute("petId", null);
        command.addPet(
                OwnerId.of(ownerId),
                PetName.of(petForm.getName()),
                BirthDate.of(LocalDate.parse(petForm.getBirthDate())),
                petForm.getType()
        );
        ra.addFlashAttribute("message", "Pet added successfully");
        return "redirect:/owners/" + ownerId;
    }

    @PostMapping("/{ownerId}/pets/{petId}/edit")
    public String updatePet(
            @PathVariable String ownerId,
            @PathVariable String petId,
            @ModelAttribute PetFormDto petForm,
            HttpServletRequest request,
            RedirectAttributes ra
    ) {
        request.setAttribute("petForm", petForm);
        request.setAttribute("ownerId", ownerId);
        request.setAttribute("petId", petId);
        command.updatePet(
                OwnerId.of(ownerId),
                PetId.of(petId),
                PetName.of(petForm.getName()),
                BirthDate.of(LocalDate.parse(petForm.getBirthDate())),
                petForm.getType()
        );
        ra.addFlashAttribute("message", "Pet updated successfully");
        return "redirect:/owners/" + ownerId;
    }

    @PostMapping("/{ownerId}/pets/{petId}/delete")
    public String deletePet(
            @PathVariable String ownerId,
            @PathVariable String petId,
            RedirectAttributes ra
    ) {
        command.removePet(OwnerId.of(ownerId), PetId.of(petId));
        ra.addFlashAttribute("message", "Pet deleted successfully");
        return "redirect:/owners/" + ownerId;
    }
}
