package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.query;

import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.OwnerListItemDto;
import io.github.dmitriirussu.petclinic.owner.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.port.in.OwnerQueryPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class MvcOwnerQueryController {

    private static final int PAGE_SIZE = 5;

    private final OwnerQueryPort query;

    public MvcOwnerQueryController(OwnerQueryPort query) {
        this.query = query;
    }

    @GetMapping("/{id}")
    public String showOwner(
            @PathVariable String id,
            @ModelAttribute("message") String message,
            Model model
    ) {
        Owner owner = query.findById(OwnerId.of(id));
        model.addAttribute("owner", owner);
        model.addAttribute("message", message);
        return "owner/result/details";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        int total      = query.countByLastName(lastName);
        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);

        List<Owner> owners = query.findPaginated(lastName, page, PAGE_SIZE);

        if (owners.isEmpty()) {
            model.addAttribute("error", "No owners found");
            model.addAttribute("message", "");
            return "owner/form/main-page-search";
        }

        if (total == 1) {
            return "redirect:/owners/" + owners.get(0).getId().value();
        }

        model.addAttribute("owners", owners.stream()
                .map(OwnerListItemDto::new)
                .toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("lastName", lastName != null ? lastName : "");
        return "owner/result/list";
    }
}