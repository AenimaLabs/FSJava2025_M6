package com.ejemplo.M6_AE2_ABPRO.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.ejemplo.M6_AE2_ABPRO.model.Evento;

public interface AgendaService {
    void guardar(Evento evento);
    List<Evento> listarTodos();
    List<Evento> buscarPorFecha(LocalDate fecha);
    Map<LocalDate, List<Evento>> listarAgrupadoPorFecha();
    void eliminarTodo();
    boolean eliminar(Evento ejemplo);
}
