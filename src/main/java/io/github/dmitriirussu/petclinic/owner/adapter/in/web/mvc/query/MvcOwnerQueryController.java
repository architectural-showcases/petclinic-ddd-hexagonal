package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.query;

import io.github.dmitriirussu.petclinic.kernel.pagination.Page;
import io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc.formdto.OwnerListItemDto;
import io.github.dmitriirussu.petclinic.owner.core.domain.aggregate.Owner;
import io.github.dmitriirussu.petclinic.owner.core.domain.valueobject.identity.OwnerId;
import io.github.dmitriirussu.petclinic.owner.core.port.in.OwnerQueryPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Page<Owner> result = query.findPaginated(lastName, page, PAGE_SIZE);

        if (result.isEmpty()) {
            model.addAttribute("error", "No owners found");
            model.addAttribute("message", "");
            return "owner/form/main-page-search";
        }

        if (result.total() == 1) {
            return "redirect:/owners/" + result.content().get(0).getId().value();
        }

        model.addAttribute("owners", result.content().stream()
                .map(OwnerListItemDto::new)
                .toList());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", result.totalPages(PAGE_SIZE));
        model.addAttribute("lastName", lastName != null ? lastName : "");
        return "owner/result/list";
    }
}