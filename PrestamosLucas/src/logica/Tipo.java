package logica;

import java.io.Serializable;

public class Tipo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
    private String descripcion;

    public Tipo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return nombre;
    }
}