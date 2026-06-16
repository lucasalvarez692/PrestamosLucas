package logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigo;
    private String nombre;
    private String descripcion;
    private Prestamo prestamoActual;
    private Tipo tipo;
    private List<Categoria> categorias;

    public Item(String codigo, String nombre, String descripcion, Tipo tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.categorias = new ArrayList<>();
        this.prestamoActual = null;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Prestamo getPrestamoActual() { return prestamoActual; }
    public void setPrestamoActual(Prestamo prestamoActual) { this.prestamoActual = prestamoActual; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public List<Categoria> getCategorias() { return categorias; }

    public void agregarCategoria(Categoria categoria) {
        if (!categorias.contains(categoria)) {
            categorias.add(categoria);
        }
    }

    public void eliminarCategoria(Categoria categoria) {
        categorias.remove(categoria);
    }

    public boolean isPrestado() {
        return prestamoActual != null;
    }

    @Override
    public String toString() {
        return nombre + " (" + codigo + ")";
    }
}