package logica;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

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

    public void agregarPrestamo(Prestamo p) {
        prestamosActivos.add(p);
    }

    public void eliminarPrestamo(Prestamo p) {
        prestamosActivos.remove(p);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Prestamo> getPrestamosActivos() { return prestamosActivos; }

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }
}