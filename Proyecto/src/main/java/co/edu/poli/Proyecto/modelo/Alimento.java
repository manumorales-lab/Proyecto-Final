package co.edu.poli.Proyecto.modelo;

import java.io.Serializable;

/**
 * Modelo que representa un alimento espacial
 * @author Manuel Morales, Darien Díaz
 * @since 2025
 */
public class Alimento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nombre;
    private int cantidad;
    private String fechaCaducidad;

    public Alimento(String id, String nombre, int cantidad, String fechaCaducidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(String fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    @Override
    public String toString() {
        return String.format("Alimento{id='%s', nombre='%s', cantidad=%d, fecha='%s'}", 
                           id, nombre, cantidad, fechaCaducidad);
    }
}