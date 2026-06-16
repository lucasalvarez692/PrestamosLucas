package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Prestamo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
        this.usuario = usuario;
        this.items = new ArrayList<>();
        this.alerta = null;
    }

    public int getId() { return id; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public Date getFechaDevolucionReal() { return fechaDevolucionReal; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<Item> getItems() { return items; }

    public Alerta getAlerta() { return alerta; }
    public void setAlerta(Alerta alerta) { this.alerta = alerta; }

    public void incluirItem(Item item) {
        if (!items.contains(item) && item.getPrestamoActual() == null) {
            items.add(item);
            item.setPrestamoActual(this);
        }
    }

    public void eliminarItem(Item item) {
        if (items.remove(item)) {
            item.setPrestamoActual(null);
        }
    }

    public void finalizarPrestamo() {
        for (Item item : items) {
            item.setPrestamoActual(null);
        }
        items.clear();
        this.fechaDevolucionReal = new Date();
        if (usuario != null) {
            usuario.eliminarPrestamo(this);
        }
    }

    public void mostrarAlerta() {
        if (alerta != null) {
            alerta.mostrar();
        }
    }

    public boolean isActivo() {
        return !items.isEmpty();
    }

    @Override
    public String toString() {
        return "Préstamo #" + id + " - " + usuario.getNombre() + " (" + items.size() + " ítems)";
    }
}