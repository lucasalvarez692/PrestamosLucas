package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa un préstamo hecho por un usuario.
 * Contiene la fecha, los ítems prestados y una posible alerta.
 */
public class Prestamo {

    // Contador estático para generar IDs únicos automáticamente
    private static int contador = 1;

    private int id;
    private Date fechaPrestamo;
    private Date fechaDevolucionReal; // null si aún no se ha devuelto
    private Usuario usuario;
    private List<Item> items;
    private Alerta alerta;

    // Constructor
    public Prestamo(Usuario usuario) {
        this.id = contador++;
        this.fechaPrestamo = new Date(); // fecha actual
        this.fechaDevolucionReal = null;
        this.usuario = usuario;
        this.items = new ArrayList<>();
        this.alerta = null;
    }

    // Agrega un ítem al préstamo y lo marca como "prestado"
    public void incluirItem(Item item) {
        items.add(item);
        item.setPrestamoActual(this); // marcar como no disponible
    }

    // Quita un ítem del préstamo
    public void eliminarItem(Item item) {
        items.remove(item);
        item.setPrestamoActual(null); // vuelve a estar disponible
    }

    // Marca el préstamo como finalizado: libera todos los ítems
    public void finalizarPrestamo() {
        this.fechaDevolucionReal = new Date();
        for (Item item : items) {
            item.setPrestamoActual(null); // cada ítem queda disponible de nuevo
        }
        usuario.eliminarPrestamo(this);
    }

    // Muestra la alerta si existe
    public void mostrarAlerta() {
        if (alerta != null) {
            alerta.mostrar();
        }
    }

    // Verifica si el préstamo está activo (no devuelto)
    public boolean estaActivo() {
        return fechaDevolucionReal == null;
    }

    // Getters y Setters
    public int getId() { return id; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public Date getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(Date f) { this.fechaDevolucionReal = f; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<Item> getItems() { return items; }
    public Alerta getAlerta() { return alerta; }
    public void setAlerta(Alerta alerta) { this.alerta = alerta; }

    @Override
    public String toString() {
        return "Préstamo #" + id + " - " + usuario.getNombre() +
               " - " + items.size() + " ítem(s) - " +
               (estaActivo() ? "ACTIVO" : "DEVUELTO");
    }
}