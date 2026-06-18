package logica;

/**
 * Representa el tipo de un ítem si es un libro o herramienta o asi
 */
public class Tipo {

    private String nombre;
    private String descripcion;

    // Constructor
    public Tipo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return nombre;
    }
}