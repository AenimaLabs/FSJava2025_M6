package com.ejemplo.M6_AE2_ABPRO;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import com.ejemplo.M6_AE2_ABPRO.model.Evento;
import com.ejemplo.M6_AE2_ABPRO.service.AgendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AgendaServiceTests {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ObjectProvider<Evento> eventoProvider;

    private Evento nuevoEvento(String titulo, LocalDate fecha, String desc, String resp) {
        Evento e = eventoProvider.getObject();
        e.setTitulo(titulo);
        e.setFecha(fecha);
        e.setDescripcion(desc);
        e.setResponsable(resp);
        return e;
    }

    @BeforeEach
    void limpiarStore() {
        agendaService.eliminarTodo();
    }

    @Test
    @DisplayName("Agregado de eventos: guarda y lista correctamente")
    void agregaEventos_y_losLista() {
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);

        agendaService.guardar(nuevoEvento("Cumpleaños", hoy, "Torta y café", "Ana"));
        agendaService.guardar(nuevoEvento("Reunión Sprint", manana, "Planificar iteración", "Luis"));

        List<Evento> todos = agendaService.listarTodos();
        assertEquals(2, todos.size(), "Debe haber 2 eventos");
        assertEquals("Cumpleaños", todos.get(0).getTitulo());
        assertEquals("Reunión Sprint", todos.get(1).getTitulo());
    }

    @Test
    @DisplayName("Recuperación por fecha: devuelve solo los del día consultado")
    void recuperaPorFecha() {
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);

        agendaService.guardar(nuevoEvento("A", hoy, "Desc A", "Ana"));
        agendaService.guardar(nuevoEvento("B", hoy, "Desc B", "Ana"));
        agendaService.guardar(nuevoEvento("C", manana, "Desc C", "Luis"));

        var delHoy = agendaService.buscarPorFecha(hoy);
        assertEquals(2, delHoy.size(), "Hoy debe tener 2 eventos");
        assertTrue(delHoy.stream().allMatch(e -> e.getFecha().equals(hoy)));

        var delManana = agendaService.buscarPorFecha(manana);
        assertEquals(1, delManana.size(), "Mañana debe tener 1 evento");
        assertEquals("C", delManana.get(0).getTitulo());
    }
}