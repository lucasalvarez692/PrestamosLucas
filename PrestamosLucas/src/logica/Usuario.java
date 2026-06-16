package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
    private String telefono;
    private String email;
    private List<Prestamo> prestamosActivos;

    public Usuario(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.prestamosActivos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Prestamo> getPrestamosActivos() { return prestamosActivos; }

    public void agregarPrestamo(Prestamo prestamo) {
        if (!prestamosActivos.contains(prestamo)) {
            prestamosActivos.add(prestamo);
        }
    }

    public void eliminarPrestamo(Prestamo prestamo) {
        prestamosActivos.remove(prestamo);
    }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}