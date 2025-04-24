package com.productos_consumidos.productos_consumidos.DTO;

import java.time.LocalDateTime;

public class ProductoDTO {
private String nombre;
    private Double precio;
    private LocalDateTime fechaRegistro;

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
