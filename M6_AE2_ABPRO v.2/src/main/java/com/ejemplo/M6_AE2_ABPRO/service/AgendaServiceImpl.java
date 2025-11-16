package com.ejemplo.M6_AE2_ABPRO.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ejemplo.M6_AE2_ABPRO.model.Evento;

@Service
public class AgendaServiceImpl implements AgendaService {

    private static final Logger log = LoggerFactory.getLogger(AgendaServiceImpl.class);

    private final List<Evento> store = new CopyOnWriteArrayList<>();

    private static final Comparator<Evento> POR_FECHA_TITULO =
            Comparator.comparing(Evento::getFecha,
                            Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(e -> {
                        String t = e.getTitulo();
                        return t == null ? "" : t.toLowerCase();
                    });

    @Override
    public void guardar(Evento evento) {
        if (evento == null) return;
        store.add(evento);
        log.debug("Evento guardado: {}", evento);
    }

    @Override
    public List<Evento> listarTodos() {
        return store.stream()
                .sorted(POR_FECHA_TITULO)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Evento> buscarPorFecha(LocalDate fecha) {
        if (fecha == null) return List.of();
        return store.stream()
                .filter(e -> fecha.equals(e.getFecha()))
                .sorted(POR_FECHA_TITULO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<LocalDate, List<Evento>> listarAgrupadoPorFecha() {
        return store.stream()
                .sorted(POR_FECHA_TITULO)
                .collect(Collectors.groupingBy(
                        Evento::getFecha,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    @Override
    public void eliminarTodo() {
        store.clear();
        log.debug("Todos los eventos fueron eliminados");
    }

    public boolean eliminar(Evento ejemplo) {
        if (ejemplo == null) return false;
        return store.removeIf(e ->
                java.util.Objects.equals(e.getTitulo(), ejemplo.getTitulo()) &&
                        java.util.Objects.equals(e.getFecha(),  ejemplo.getFecha())  &&
                        java.util.Objects.equals(e.getResponsable(), ejemplo.getResponsable())
        );
    }
}
