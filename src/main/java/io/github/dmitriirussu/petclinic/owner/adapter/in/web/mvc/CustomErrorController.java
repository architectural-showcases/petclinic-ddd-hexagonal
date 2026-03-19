/*package io.github.dmitriirussu.petclinic.owner.adapter.in.web.mvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status  = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        int statusCode = status != null ? Integer.parseInt(status.toString()) : 500;

        model.addAttribute("status", statusCode);
        model.addAttribute("message", message != null ? message.toString() : "");
        return "error";
    }

    @GetMapping("/oups")
    public String triggerError() {
        throw new RuntimeException("Expected exception — demo of error handling");
    }
}*/
