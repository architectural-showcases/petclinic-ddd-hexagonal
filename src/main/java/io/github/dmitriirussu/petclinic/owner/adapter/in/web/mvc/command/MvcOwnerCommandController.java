package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.command;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.OwnerFormDto;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.Address;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.OwnerName;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.personal.PhoneNumber;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerCommandPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/owners")
public class MvcOwnerCommandController {

    private final OwnerCommandPort command;

    public MvcOwnerCommandController(OwnerCommandPort command) {
        this.command = command;
    }

    @PostMapping
    public String create(
            @ModelAttribute OwnerFormDto ownerForm,
            HttpServletRequest request,
            RedirectAttributes ra
    ) {
        request.setAttribute("ownerForm", ownerForm);
        request.setAttribute("ownerId", null);
        OwnerId id = command.createOwner(
                OwnerName.of(ownerForm.getFirstName(), ownerForm.getLastName()),
                Address.of(ownerForm.getStreet(), ownerForm.getCity()),
                PhoneNumber.of(ownerForm.getTelephone())
        );
        ra.addFlashAttribute("message", "Owner created successfully");
        return "redirect:/owners/" + id.value();
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @ModelAttribute OwnerFormDto ownerForm,
            HttpServletRequest request,
            RedirectAttributes ra
    ) {
        request.setAttribute("ownerForm", ownerForm);
        request.setAttribute("ownerId", id);
        command.updateOwner(
                OwnerId.of(id),
                OwnerName.of(ownerForm.getFirstName(), ownerForm.getLastName()),
                Address.of(ownerForm.getStreet(), ownerForm.getCity()),
                PhoneNumber.of(ownerForm.getTelephone())
        );
        ra.addFlashAttribute("message", "Owner updated successfully");
        return "redirect:/owners/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        command.deleteOwner(OwnerId.of(id));
        ra.addFlashAttribute("message", "Owner deleted successfully");
        return "redirect:/owners";
    }
}
