package control;

import logica.*;

import java.util.ArrayList;
import java.util.List;

public class Controladora {

    private static Controladora instance;

    // Listas con todos los datos del sistema
    private List<Usuario> usuarios;
    private List<Item> items;
    private List<Prestamo> prestamos;
    private List<Categoria> categorias;
    private List<Tipo> tipos;

    // Constructor privado para evitar que alguien cree más de una instancia
    private Controladora() {
        usuarios = new ArrayList<>();
        items = new ArrayList<>();
        prestamos = new ArrayList<>();
        categorias = new ArrayList<>();
        tipos = new ArrayList<>();
        cargarDatosDePrueba(); // carga algunos datos al iniciar
    }

    // Método para obtener la instancia única
    public static Controladora getInstance() {
        if (instance == null) {
            instance = new Controladora();
        }
        return instance;
    }

    // Datos para probarla

    /**
     * Agrega datos iniciales para probar el sistema sin tener que escribir todo
     */
    private void cargarDatosDePrueba() {
        // Tipos
        Tipo t1 = new Tipo("Libro", "Material de lectura");
        Tipo t2 = new Tipo("Herramienta", "Herramienta de trabajo");
        tipos.add(t1);
        tipos.add(t2);

        // Categorías
        Categoria c1 = new Categoria("Tecnología", "Libros y equipos tech");
        Categoria c2 = new Categoria("Arte", "Materiales artísticos");
        categorias.add(c1);
        categorias.add(c2);

        // Ítems
        Item i1 = new Item("I001", "Clean Code", "Libro de programación", t1);
        i1.agregarCategoria(c1);
        Item i2 = new Item("I002", "Taladro Bosch", "Taladro eléctrico", t2);
        items.add(i1);
        items.add(i2);

        // Usuarios
        Usuario u1 = new Usuario("Ana García", "8888-1111", "ana@mail.com");
        Usuario u2 = new Usuario("Luis Pérez", "7777-2222", "luis@mail.com");
        usuarios.add(u1);
        usuarios.add(u2);
    }

    // Parte de los usuarios gestion  y eso

    /** Crea un nuevo usuario y lo agrega a la lista */
    public boolean crearUsuario(String nombre, String telefono, String email) {
        if (nombre.isEmpty() || email.isEmpty()) return false;
        usuarios.add(new Usuario(nombre, telefono, email));
        return true;
    }

    /** Modifica los datos de un usuario existente */
    public boolean modificarUsuario(Usuario u, String nombre, String telefono, String email) {
        if (u == null) return false;
        u.setNombre(nombre);
        u.setTelefono(telefono);
        u.setEmail(email);
        return true;
    }

    /** Elimina un usuario si no tiene préstamos activos */
    public boolean eliminarUsuario(Usuario u) {
        if (u == null) return false;
        if (!u.getPrestamosActivos().isEmpty()) return false; // no se puede eliminar si tiene préstamos
        return usuarios.remove(u);
    }

    public List<Usuario> getUsuarios() { return usuarios; }

    // Los items y su gestion

    /** Crea un nuevo ítem */
    public boolean crearItem(String codigo, String nombre, String descripcion, Tipo tipo) {
        if (codigo.isEmpty() || nombre.isEmpty()) return false;
        // verificar que no exista el código
        for (Item i : items) {
            if (i.getCodigo().equals(codigo)) return false;
        }
        items.add(new Item(codigo, nombre, descripcion, tipo));
        return true;
    }

    /** Modifica un ítem existente */
    public boolean modificarItem(Item item, String codigo, String nombre, String descripcion, Tipo tipo) {
        if (item == null) return false;
        item.setCodigo(codigo);
        item.setNombre(nombre);
        item.setDescripcion(descripcion);
        item.setTipo(tipo);
        return true;
    }

    /** Elimina un ítem si está disponible (no prestado) */
    public boolean eliminarItem(Item item) {
        if (item == null || !item.estaDisponible()) return false;
        return items.remove(item);
    }

    public List<Item> getItems() { return items; }

    /** Devuelve solo los ítems disponibles para prestar */
    public List<Item> getItemsDisponibles() {
        List<Item> disponibles = new ArrayList<>();
        for (Item i : items) {
            if (i.estaDisponible()) disponibles.add(i);
        }
        return disponibles;
    }

    // Prestamos y su gestion

    /** Crea un nuevo préstamo para un usuario con una lista de ítems */
    public boolean hacerPrestamo(Usuario usuario, List<Item> itemsSeleccionados) {
        if (usuario == null || itemsSeleccionados.isEmpty()) return false;

        Prestamo p = new Prestamo(usuario);
        for (Item item : itemsSeleccionados) {
            if (!item.estaDisponible()) return false; // no se puede prestar algo ya prestado
            p.incluirItem(item);
        }

        usuario.agregarPrestamo(p);
        prestamos.add(p);
        return true;
    }

    /** Finaliza (devuelve) un préstamo */
    public boolean retornarPrestamo(Prestamo p) {
        if (p == null || !p.estaActivo()) return false;
        p.finalizarPrestamo();
        return true;
    }

    /** Agrega una alerta a un préstamo */
    public boolean agregarAlerta(Prestamo p, String tipo, int intervaloDias, String mensaje) {
        if (p == null) return false;
        Alerta alerta = new Alerta(tipo, intervaloDias, mensaje, p.getFechaPrestamo());
        p.setAlerta(alerta);
        return true;
    }

    /** Devuelve todos los préstamos (activos e histórico) */
    public List<Prestamo> getPrestamos() { return prestamos; }

    /** Devuelve solo los préstamos activos */
    public List<Prestamo> getPrestamosActivos() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.estaActivo()) activos.add(p);
        }
        return activos;
    }

    // Categorias add y delete

    public boolean crearCategoria(String nombre, String descripcion) {
        if (nombre.isEmpty()) return false;
        categorias.add(new Categoria(nombre, descripcion));
        return true;
    }

    public boolean eliminarCategoria(Categoria c) {
        return categorias.remove(c);
    }

    public List<Categoria> getCategorias() { return categorias; }

    // Tipos add y delete

    public boolean crearTipo(String nombre, String descripcion) {
        if (nombre.isEmpty()) return false;
        tipos.add(new Tipo(nombre, descripcion));
        return true;
    }

    public boolean eliminarTipo(Tipo t) {
        return tipos.remove(t);
    }

    public List<Tipo> getTipos() { return tipos; }

    // Reportes simples ----

    /** Reporte de todos los usuarios */
    public String reporteUsuarios() {
        StringBuilder sb = new StringBuilder("=== USUARIOS ===\n");
        for (Usuario u : usuarios) {
            sb.append(u).append(" | Préstamos activos: ")
              .append(u.getPrestamosActivos().size()).append("\n");
        }
        return sb.toString();
    }

    /** Reporte de todos los ítems */
    public String reporteItems() {
        StringBuilder sb = new StringBuilder("=== ÍTEMS ===\n");
        for (Item i : items) {
            sb.append(i).append("\n");
        }
        return sb.toString();
    }

    /** Muestra alertas de préstamos que ya deberían notificarse */
    public void mostrarAlertasPendientes() {
        for (Prestamo p : prestamos) {
            if (p.estaActivo()) {
                p.mostrarAlerta();
            }
        }
    }
}