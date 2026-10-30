package com.alonsoravanalrubenvelazquez.ep_ms_java_eventos.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String recinto;

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Integer precio;

    @NotNull(message = "El aforo máximo es obligatorio")
    @Positive(message = "El aforo máximo debe ser mayor que cero")
    @Column(nullable = false)
    private Integer aforoMaximo;

    @NotNull(message = "El aforo disponible es obligatorio")
    @PositiveOrZero(message = "El aforo disponible no puede ser negativo")
    @Column(nullable = false)
    private Integer aforoDisponible;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comuna_id", nullable = false)
    private Comuna comuna;

    public Evento(String nombre, String descripcion, LocalDateTime fecha, String recinto, Integer precio,
            Integer aforoMaximo, Integer aforoDisponible, Comuna comuna) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.recinto = recinto;
        this.precio = precio;
        this.aforoMaximo = aforoMaximo;
        this.aforoDisponible = aforoDisponible;
        this.comuna = comuna;
    }

    public Evento() {
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getRecinto() {
        return recinto;
    }

    public void setRecinto(String recinto) {
        this.recinto = recinto;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getAforoMaximo() {
        return aforoMaximo;
    }

    public void setAforoMaximo(Integer aforoMaximo) {
        this.aforoMaximo = aforoMaximo;
    }

    public Integer getAforoDisponible() {
        return aforoDisponible;
    }

    public void setAforoDisponible(Integer aforoDisponible) {
        this.aforoDisponible = aforoDisponible;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

}
