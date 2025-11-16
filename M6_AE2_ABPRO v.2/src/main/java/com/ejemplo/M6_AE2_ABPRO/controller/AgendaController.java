package com.ejemplo.M6_AE2_ABPRO.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ejemplo.M6_AE2_ABPRO.model.Evento;
import com.ejemplo.M6_AE2_ABPRO.service.AgendaService;

@Controller
public class AgendaController {

    private final AgendaService agendaService;
    private final ObjectProvider<Evento> eventoProvider;

    @Autowired
    public AgendaController(AgendaService agendaService, ObjectProvider<Evento> eventoProvider) {
        this.agendaService = agendaService;
        this.eventoProvider = eventoProvider;
    }

    @GetMapping({"/", ""})
    public String raiz() {
        return "redirect:/eventos";
    }

    @GetMapping("/eventos/nuevo")
    public String mostrarFormulario(Model model) {
        if (!model.containsAttribute("evento")) {
            model.addAttribute("evento", eventoProvider.getObject());
        }
        return "nuevoEvento"; // nombre de la plantilla Thymeleaf
    }

    @PostMapping("/eventos")
    public String guardarEvento(@Valid @ModelAttribute("evento") Evento evento,
                                BindingResult result,
                                RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "nuevoEvento";
        }
        agendaService.guardar(evento);
        redirect.addFlashAttribute("msgOk", "Evento registrado correctamente");
        return "redirect:/eventos"; // PRG pattern
    }

    @GetMapping("/eventos")
    public String listarAgrupado(Model model) {
        Map<LocalDate, List<Evento>> agrupado = agendaService.listarAgrupadoPorFecha();
        model.addAttribute("eventosPorFecha", agrupado);
        return "eventos"; // nombre de la plantilla Thymeleaf
    }

    // Borrar evento individual
    @PostMapping("/eventos/borrar")
    public String borrarEvento(
            @org.springframework.web.bind.annotation.RequestParam("titulo") String titulo,
            @org.springframework.web.bind.annotation.RequestParam("fecha")
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            java.time.LocalDate fecha,
            @org.springframework.web.bind.annotation.RequestParam("responsable") String responsable,
            RedirectAttributes redirect) {

        Evento tmp = eventoProvider.getObject();
        tmp.setTitulo(titulo);
        tmp.setFecha(fecha);
        tmp.setResponsable(responsable);

        boolean ok = agendaService.eliminar(tmp);
        if (ok) {
            redirect.addFlashAttribute("msgOk", "Evento eliminado correctamente.");
        } else {
            redirect.addFlashAttribute("msgErr", "No se encontró el evento a eliminar.");
        }
        return "redirect:/eventos";
    }

    // Borrar todos
    @PostMapping("/eventos/borrar-todo")
    public String borrarTodo(RedirectAttributes redirect) {
        agendaService.eliminarTodo();
        redirect.addFlashAttribute("msgOk", "Todos los eventos fueron eliminados.");
        return "redirect:/eventos";
    }

}