package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Prestamo {

    private static int contador = 1;

    private int id;
    private Date fechaPrestamo;
    private Date fechaDevolucionReal; 
    private Usuario usuario;
    private List<Item> items;
    private Alerta alerta;

    public Prestamo(Usuario usuario) {
        this.id = contador++;
        this.fechaPrestamo = new Date(); 
        this.fechaDevolucionReal = null;
        this.usuario = usuario;
        this.items = new ArrayList<>();
        this.alerta = null;
    }

    public void incluirItem(Item item) {
        items.add(item);
        item.setPrestamoActual(this); 
    }

    public void eliminarItem(Item item) {
        items.remove(item);
        item.setPrestamoActual(null); 
    }

    public void finalizarPrestamo() {
        this.fechaDevolucionReal = new Date();
        for (Item item : items) {
            item.setPrestamoActual(null); 
        }
        usuario.eliminarPrestamo(this);
    }

    public void mostrarAlerta() {
        if (alerta != null) {
            alerta.mostrar();
        }
    }

    public boolean estaActivo() {
        return fechaDevolucionReal == null;
    }

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