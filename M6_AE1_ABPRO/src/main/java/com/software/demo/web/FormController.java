package com.software.demo.web;

import com.software.demo.model.PersonaForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    @GetMapping({"/", "/form"})
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("persona")) {
            model.addAttribute("persona", new PersonaForm());
        }
        return "form"; // templates/form.html
    }

    @PostMapping("/form")
    public String procesarFormulario(@Valid @ModelAttribute("persona") PersonaForm persona,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "form";
        }
        return "success";
    }
}