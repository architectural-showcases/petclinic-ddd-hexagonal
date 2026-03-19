package io.github.dmitriirussu.petclinic.vet.adapter.in.web.mvc;

import io.github.dmitriirussu.petclinic.vet.domain.Vet;
import io.github.dmitriirussu.petclinic.vet.port.in.VetQueryPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/vets")
public class MvcVetQueryController {

    private static final int PAGE_SIZE = 5;

    private final VetQueryPort query;

    public MvcVetQueryController(VetQueryPort query) {
        this.query = query;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        int total      = query.countAll();
        int totalPages = (int) Math.ceil((double) total / PAGE_SIZE);
        List<Vet> vets = query.findAll(page, PAGE_SIZE);

        model.addAttribute("vets", vets);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "vet/result/list";
    }
}
