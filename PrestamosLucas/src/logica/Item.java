package logica;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String codigo;
    private String nombre;
    private String descripcion;
    private Prestamo prestamoActual;
    private List<Categoria> categorias;
    private Tipo tipo;

    public Item(String codigo, String nombre, String descripcion, Tipo tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.categorias = new ArrayList<>();
        this.prestamoActual = null;
    }

    public boolean estaDisponible() {
        return prestamoActual == null;
    }

    public void agregarCategoria(Categoria c) {
        categorias.add(c);
    }

    public void eliminarCategoria(Categoria c) {
        categorias.remove(c);
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Prestamo getPrestamoActual() { return prestamoActual; }
    public void setPrestamoActual(Prestamo p) { this.prestamoActual = p; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public List<Categoria> getCategorias() { return categorias; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + (estaDisponible() ? " [Disponible]" : " [Prestado]");
    }
}