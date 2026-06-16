package control;

import logica.*;
import java.io.*;
import java.util.*;

public class Controladora implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Item> items;
    private List<Usuario> usuarios;
    private List<Categoria> categorias;
    private List<Tipo> tipos;
    private List<Prestamo> prestamos;
    private static final String ARCHIVO = "datos.dat";

    private static Controladora instance;

    private Controladora() {
        items = new ArrayList<>();
        usuarios = new ArrayList<>();
        categorias = new ArrayList<>();
        tipos = new ArrayList<>();
        prestamos = new ArrayList<>();
        cargarDatos();
    }

    public static Controladora getInstance() {
        if (instance == null) {
            instance = new Controladora();
        }
        return instance;
    }

    // ========== PERSISTENCIA ==========
    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(this);
        } catch (IOException e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    public void cargarDatos() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            Controladora cargada = (Controladora) ois.readObject();
            this.items = cargada.items;
            this.usuarios = cargada.usuarios;
            this.categorias = cargada.categorias;
            this.tipos = cargada.tipos;
            this.prestamos = cargada.prestamos;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }

    // ========== CRUD ITEMS ==========
    public void crearItem(String codigo, String nombre, String descripcion, Tipo tipo) {
        items.add(new Item(codigo, nombre, descripcion, tipo));
    }

    public List<Item> getItems() { return items; }

    public void modificarItem(Item item, String codigo, String nombre, String descripcion, Tipo tipo) {
        if (item != null) {
            item.setCodigo(codigo);
            item.setNombre(nombre);
            item.setDescripcion(descripcion);
            item.setTipo(tipo);
        }
    }

    public void eliminarItem(Item item) {
        if (item != null && item.getPrestamoActual() == null) {
            items.remove(item);
        }
    }

    // ========== CRUD USUARIOS ==========
    public void crearUsuario(String nombre, String telefono, String email) {
        usuarios.add(new Usuario(nombre, telefono, email));
    }

    public List<Usuario> getUsuarios() { return usuarios; }

    public void modificarUsuario(Usuario usuario, String nombre, String telefono, String email) {
        if (usuario != null) {
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setEmail(email);
        }
    }

    public void eliminarUsuario(Usuario usuario) {
        if (usuario != null && usuario.getPrestamosActivos().isEmpty()) {
            usuarios.remove(usuario);
        }
    }

    // ========== CRUD CATEGORÍAS ==========
    public void crearCategoria(String nombre, String descripcion) {
        categorias.add(new Categoria(nombre, descripcion));
    }

    public List<Categoria> getCategorias1() { return categorias; }

    public void eliminarCategoria(Categoria categoria) {
        if (categoria != null) {
            categorias.remove(categoria);
            for (Item item : items) {
                item.eliminarCategoria(categoria);
            }
        }
    }

    // ========== CRUD TIPOS ==========
    public void crearTipo(String nombre, String descripcion) {
        tipos.add(new Tipo(nombre, descripcion));
    }

    public List<Tipo> getTipos1() { return tipos; }

    public void eliminarTipo(Tipo tipo) {
        if (tipo != null) {
            for (Item item : items) {
                if (item.getTipo().equals(tipo)) {
                    return;
                }
            }
            tipos.remove(tipo);
        }
    }

    // ========== PRÉSTAMOS ==========
    public void hacerPrestamo(Usuario usuario, List<Item> itemsSeleccionados) {
        if (usuario == null || itemsSeleccionados == null || itemsSeleccionados.isEmpty()) {
            return;
        }

        Prestamo prestamo = new Prestamo(usuario);
        for (Item item : itemsSeleccionados) {
            if (item.getPrestamoActual() == null) {
                prestamo.incluirItem(item);
            }
        }

        if (!prestamo.getItems().isEmpty()) {
            prestamos.add(prestamo);
            usuario.agregarPrestamo(prestamo);
        }
    }

    public List<Prestamo> getPrestamos() { return prestamos; }

    public void retornarItem(Prestamo prestamo, Item item) {
        if (prestamo != null && item != null) {
            prestamo.eliminarItem(item);
            if (prestamo.getItems().isEmpty()) {
                finalizarPrestamo(prestamo);
            }
        }
    }

    public void finalizarPrestamo(Prestamo prestamo) {
        if (prestamo != null) {
            prestamo.finalizarPrestamo();
            prestamos.remove(prestamo);
        }
    }

    public void agregarAlerta(Prestamo prestamo, String tipo, int intervaloDias, String mensaje) {
        if (prestamo != null) {
            Alerta alerta = new Alerta(tipo, intervaloDias, mensaje, prestamo.getFechaPrestamo());
            prestamo.setAlerta(alerta);
        }
    }

    // ========== REPORTES ==========
    public String reporteUsuarios() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE USUARIOS ===\n\n");

        List<Usuario> ordenados = new ArrayList<>(usuarios);
        ordenados.sort(Comparator.comparing(Usuario::getNombre));

        for (Usuario u : ordenados) {
            sb.append("Usuario: ").append(u.getNombre()).append("\n");
            sb.append("  Teléfono: ").append(u.getTelefono()).append("\n");
            sb.append("  Email: ").append(u.getEmail()).append("\n");

            List<Prestamo> activos = u.getPrestamosActivos();
            if (!activos.isEmpty()) {
                sb.append("  Préstamos activos:\n");
                for (Prestamo p : activos) {
                    sb.append("    - #").append(p.getId()).append(": ");
                    for (Item item : p.getItems()) {
                        sb.append(item.getNombre()).append(", ");
                    }
                    sb.setLength(sb.length() - 2);
                    sb.append("\n");
                }
            } else {
                sb.append("  Sin préstamos activos\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String reporteItems() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE DE ÍTEMS ===\n\n");

        List<Item> ordenados = new ArrayList<>(items);
        ordenados.sort(Comparator.comparing(Item::getNombre));

        for (Item item : ordenados) {
            sb.append("Ítem: ").append(item.getNombre()).append("\n");
            sb.append("  Código: ").append(item.getCodigo()).append("\n");
            sb.append("  Tipo: ").append(item.getTipo().getNombre()).append("\n");

            if (!item.getCategorias().isEmpty()) {
                sb.append("  Categorías: ");
                for (Categoria c : item.getCategorias()) {
                    sb.append(c.getNombre()).append(", ");
                }
                sb.setLength(sb.length() - 2);
                sb.append("\n");
            }

            if (item.getPrestamoActual() != null) {
                sb.append("  Estado: PRESTADO a ")
                  .append(item.getPrestamoActual().getUsuario().getNombre())
                  .append("\n");
            } else {
                sb.append("  Estado: DISPONIBLE\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // ========== MÉTODOS ADICIONALES ==========
    public List<Item> getItemsOrdenados() {
        List<Item> ordenados = new ArrayList<>(items);
        ordenados.sort(Comparator.comparing(Item::getNombre));
        return ordenados;
    }

    public List<Usuario> getUsuariosOrdenados() {
        List<Usuario> ordenados = new ArrayList<>(usuarios);
        ordenados.sort(Comparator.comparing(Usuario::getNombre));
        return ordenados;
    }

    public Map<Categoria, List<Item>> getItemsPorCategoria() {
        Map<Categoria, List<Item>> mapa = new HashMap<>();
        for (Item item : items) {
            for (Categoria categoria : item.getCategorias()) {
                mapa.computeIfAbsent(categoria, k -> new ArrayList<>()).add(item);
            }
        }
        return mapa;
    }

    public Map<Tipo, List<Item>> getItemsPorTipo() {
        Map<Tipo, List<Item>> mapa = new HashMap<>();
        for (Item item : items) {
            mapa.computeIfAbsent(item.getTipo(), k -> new ArrayList<>()).add(item);
        }
        return mapa;
    }

    public void mostrarAlertasPendientes() {
        for (Prestamo p : prestamos) {
            p.mostrarAlerta();
            Alerta alerta = p.getAlerta();
            if (alerta != null && alerta.getTipo().equals("recurrente")) {
                alerta.calcularProximaFecha();
            }
        }
    }

    // ========== GETTERS DE COLECCIONES ==========
    public List<Categoria> getCategorias() { return categorias; }
    public List<Tipo> getTipos() { return tipos; }
}