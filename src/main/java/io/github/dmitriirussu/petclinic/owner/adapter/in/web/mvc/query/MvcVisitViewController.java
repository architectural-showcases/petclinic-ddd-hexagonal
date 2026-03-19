package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.query;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.VisitFormDto;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.entity.Pet;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerQueryPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owners")
public class MvcVisitViewController {

    private final OwnerQueryPort query;

    public MvcVisitViewController(OwnerQueryPort query) {
        this.query = query;
    }

    @GetMapping("/{ownerId}/pets/{petId}/visits/new")
    public String showAddVisitForm(
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
        model.addAttribute("pet", pet);
        model.addAttribute("visitForm", new VisitFormDto());
        model.addAttribute("error", "");
        return "owner/form/visit-form";
    }
}
