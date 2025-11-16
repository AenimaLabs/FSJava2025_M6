package com.ejemplo.M6_AE2_ABPRO.model;

import java.time.LocalDate;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Evento {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 80, message = "El título no debe exceder 80 caracteres")
    private String titulo;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha debe ser hoy o futura")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // yyyy-MM-dd (input type="date")
    private LocalDate fecha;

    @Size(max = 200, message = "La descripción no debe exceder 200 caracteres")
    private String descripcion;

    @NotBlank(message = "El responsable es obligatorio")
    @Size(max = 60, message = "El responsable no debe exceder 60 caracteres")
    private String responsable;

    public Evento() {
    }

    public Evento(String titulo, LocalDate fecha, String descripcion, String responsable) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.responsable = responsable;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    // (Opcional) equals/hashCode para usar en colecciones, si luego agrupa o evita duplicados
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento e = (Evento) o;
        return java.util.Objects.equals(titulo, e.titulo)
                && java.util.Objects.equals(fecha, e.fecha)
                && java.util.Objects.equals(responsable, e.responsable);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(titulo, fecha, responsable);
    }

    @Override
    public String toString() {
        return "Evento{titulo='" + titulo + "', fecha=" + fecha +
                ", responsable='" + responsable + "'}";
    }
}