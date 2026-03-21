package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.query;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.OwnerFormDto;
import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.port.in.OwnerQueryPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owners")
public class MvcOwnerViewController {

    private final OwnerQueryPort query;

    public MvcOwnerViewController(OwnerQueryPort query) { this.query   = query;}

    @GetMapping
    public String showSearchForm(Model model) {
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        return "owner/form/main-page-search";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ownerForm", new OwnerFormDto());
        model.addAttribute("ownerId", null);
        model.addAttribute("error", "");
        return "owner/form/create-edit-form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Owner owner = query.findById(OwnerId.of(id));
        model.addAttribute("ownerForm", OwnerFormDto.from(owner));
        model.addAttribute("ownerId", id);
        model.addAttribute("error", "");
        return "owner/form/create-edit-form";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteForm(@PathVariable String id, Model model) {
        Owner owner = query.findById(OwnerId.of(id));
        model.addAttribute("owner", owner);
        return "owner/form/delete-confirm-form";
    }
}
