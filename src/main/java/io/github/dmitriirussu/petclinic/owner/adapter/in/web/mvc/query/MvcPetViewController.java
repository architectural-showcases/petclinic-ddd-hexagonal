package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.query;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.PetFormDto;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerQueryPort;
import io.github.dmitriirussu.petclinic.owner.port.out.PetTypeCatalog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
public class MvcPetViewController {

    private final OwnerQueryPort query;
    private final PetTypeCatalog petTypeCatalog;

    public MvcPetViewController(OwnerQueryPort query, PetTypeCatalog petTypeCatalog) {
        this.query         = query;
        this.petTypeCatalog = petTypeCatalog;
    }

    @GetMapping("/{ownerId}/pets/new")
    public String showAddPetForm(@PathVariable String ownerId, Model model) {
        Owner owner = query.findById(OwnerId.of(ownerId));
        model.addAttribute("owner", owner);
        model.addAttribute("petForm", new PetFormDto());
        model.addAttribute("petId", null);
        model.addAttribute("petTypes", petTypeCatalog.getAllTypes());
        model.addAttribute("error", "");
        return "owner/form/pet-form";
    }

    @GetMapping("/{ownerId}/pets/{petId}/edit")
    public String showEditPetForm(
            @PathVariable String ownerId,
            @PathVariable String petId,
            Model model
    ) {
        Owner owner = query.findById(OwnerId.of(ownerId));
        Pet pet = owner.getPets().stream()
                .filter(p -> p.getId().value().equals(petId))
                .findFirst()
                .orElseThrow();
        model.addAttribute("owner", owner);
        model.addAttribute("petForm", PetFormDto.from(pet));
        model.addAttribute("petId", petId);
        model.addAttribute("petTypes", petTypeCatalog.getAllTypes());
        model.addAttribute("error", "");
        return "owner/form/pet-form";
    }
}
