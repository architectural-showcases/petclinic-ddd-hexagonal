package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "welcome";
    }

    @GetMapping("/oups")
    public String oups(Model model) {
        System.out.println("=== OUPS CALLED");
        model.addAttribute("status", 500);
        model.addAttribute("message", "Expected exception — demo of error handling");
        return "oups";
    }
}
