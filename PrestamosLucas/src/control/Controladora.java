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
        Tipo tJuego    = new Tipo("Videojuego",  "Juego para consola o PC");
        Tipo tConsola  = new Tipo("Consola",     "Sistema de videojuegos");
        Tipo tControl  = new Tipo("Control",     "Mando o gamepad para consola");
        Tipo tPelicula = new Tipo("Película DVD","Película en formato DVD");
        Tipo tCD       = new Tipo("CD de Música","Disco de audio compacto");

        tipos.add(tJuego);
        tipos.add(tConsola);
        tipos.add(tControl);
        tipos.add(tPelicula);
        tipos.add(tCD);

        // Categorias
        Categoria cAccion      = new Categoria("Acción",        "Juegos y películas de acción intensa");
        Categoria cRPG         = new Categoria("RPG",           "Juegos de rol y aventura");
        Categoria cDeportes    = new Categoria("Deportes",      "Juegos y contenido deportivo");
        Categoria cTerror      = new Categoria("Terror",        "Juegos y películas de terror");
        Categoria cAnimacion   = new Categoria("Animación",     "Películas y series animadas");
        Categoria cScienceFi   = new Categoria("Ciencia Ficción","Temática futurista y espacial");
        Categoria cPlayStation = new Categoria("PlayStation",   "Productos de la familia PS");
        Categoria cXbox        = new Categoria("Xbox",          "Productos de la familia Xbox");
        Categoria cNintendo    = new Categoria("Nintendo",      "Productos de Nintendo");
        Categoria cRetro       = new Categoria("Retro",         "Clásicos de generaciones anteriores");
        Categoria cBandaSonora = new Categoria("Banda Sonora",  "Soundtracks de videojuegos o películas");

        categorias.add(cAccion);
        categorias.add(cRPG);
        categorias.add(cDeportes);
        categorias.add(cTerror);
        categorias.add(cAnimacion);
        categorias.add(cScienceFi);
        categorias.add(cPlayStation);
        categorias.add(cXbox);
        categorias.add(cNintendo);
        categorias.add(cRetro);
        categorias.add(cBandaSonora);

        // ===== ÍTEMS =====

        // -- Videojuegos --
        Item g1 = new Item("VJ001", "The Last of Us Part I",     "Aventura postapocalíptica de Naughty Dog",      tJuego);
        g1.agregarCategoria(cAccion); g1.agregarCategoria(cPlayStation);

        Item g2 = new Item("VJ002", "God of War Ragnarök",       "Aventura mitológica nórdica de Santa Monica",   tJuego);
        g2.agregarCategoria(cAccion); g2.agregarCategoria(cPlayStation);

        Item g3 = new Item("VJ003", "Lego StarWars Collection",          "videojuego de acción y aventuras que abarca las nueve películas principales de la saga Skywalker",          tJuego);
        g3.agregarCategoria(cRetro); g3.agregarCategoria(cRetro);

        Item g4 = new Item("VJ004", "The Legend of Zelda: TOTK", "Aventura épica en Hyrule de Nintendo",          tJuego);
        g4.agregarCategoria(cRPG); g4.agregarCategoria(cNintendo);

        Item g5 = new Item("VJ005", "Resident Evil 4 Remake",    "Survival horror de Capcom remasterizado",       tJuego);
        g5.agregarCategoria(cTerror); g5.agregarCategoria(cAccion);

        Item g6 = new Item("VJ006", "Halo Infinite",             "Shooter espacial de 343 Industries",           tJuego);
        g6.agregarCategoria(cAccion); g6.agregarCategoria(cXbox); g6.agregarCategoria(cScienceFi);

        Item g7 = new Item("VJ007", "FIFA 23",                   "Simulador de fútbol de EA Sports",              tJuego);
        g7.agregarCategoria(cDeportes);

        Item g8 = new Item("VJ008", "Super Mario Odyssey",       "Plataformero 3D de Nintendo Switch",            tJuego);
        g8.agregarCategoria(cNintendo);

        Item g9 = new Item("VJ009", "GoldenEye 007 (N64)",       "Clásico shooter de Nintendo 64",                tJuego);
        g9.agregarCategoria(cRetro); g9.agregarCategoria(cNintendo); g9.agregarCategoria(cAccion);

        Item g10 = new Item("VJ010","Final Fantasy VII Rebirth",  "RPG de Square Enix, secuela del Remake",       tJuego);
        g10.agregarCategoria(cRPG); g10.agregarCategoria(cPlayStation);

        // -- Consolas --
        Item c1 = new Item("CS001", "PlayStation 5",             "Consola de nueva generación de Sony",           tConsola);
        c1.agregarCategoria(cPlayStation);

        Item c2 = new Item("CS002", "Xbox Series X",             "Consola de nueva generación de Microsoft",      tConsola);
        c2.agregarCategoria(cXbox);

        Item c3 = new Item("CS003", "Nintendo Switch OLED",      "Consola híbrida portátil/sobremesa",            tConsola);
        c3.agregarCategoria(cNintendo);

        Item c4 = new Item("CS004", "PlayStation 2",             "Consola clásica de Sony - la más vendida",      tConsola);
        c4.agregarCategoria(cPlayStation); c4.agregarCategoria(cRetro);

        Item c5 = new Item("CS005", "Nintendo Dsi",       "Consola portátil retro de Nintendo",            tConsola);
        c5.agregarCategoria(cNintendo); c5.agregarCategoria(cRetro);

        // -- Controles --
        Item ct1 = new Item("CT001", "DualSense PS5",            "Control háptico oficial de PlayStation 5",      tControl);
        ct1.agregarCategoria(cPlayStation);

        Item ct2 = new Item("CT002", "Xbox Elite Series 2",      "Control premium con palancas intercambiables",  tControl);
        ct2.agregarCategoria(cXbox);

        Item ct3 = new Item("CT003", "Joy-Con (Neón)",           "Controles desmontables del Nintendo Switch",    tControl);
        ct3.agregarCategoria(cNintendo);

        Item ct4 = new Item("CT004", "Control N64 Original",     "Mando clásico de tres garras de Nintendo 64",  tControl);
        ct4.agregarCategoria(cNintendo); ct4.agregarCategoria(cRetro);

        // -- Películas DVD --
        Item p1 = new Item("DVD001","The Matrix (DVD)",           "Película de ciencia ficción de Wachowski",      tPelicula);
        p1.agregarCategoria(cScienceFi); p1.agregarCategoria(cAccion);

        Item p2 = new Item("DVD002","Interstellar (DVD)",         "Drama espacial dirigido por Nolan",             tPelicula);
        p2.agregarCategoria(cScienceFi);

        Item p3 = new Item("DVD003","Avengers: Endgame (DVD)",    "Épica conclusión del universo Marvel",          tPelicula);
        p3.agregarCategoria(cAccion);

        Item p4 = new Item("DVD004","Toy Story (DVD)",            "Clásico de animación de Pixar",                tPelicula);
        p4.agregarCategoria(cAnimacion);

        Item p5 = new Item("DVD005","IT: Capítulo 1 (DVD)",       "Película de terror basada en Stephen King",     tPelicula);
        p5.agregarCategoria(cTerror);

        // -- CDs de música --
        Item cd1 = new Item("CD001","OST The Last of Us",         "Banda sonora original compuesta por Gustavo Santaolalla", tCD);
        cd1.agregarCategoria(cBandaSonora); cd1.agregarCategoria(cPlayStation);

        Item cd2 = new Item("CD002","OST Final Fantasy VII",      "Banda sonora clásica de Nobuo Uematsu",        tCD);
        cd2.agregarCategoria(cBandaSonora); cd2.agregarCategoria(cRPG);

        Item cd3 = new Item("CD003","OST Halo 3",                 "Épica banda sonora coral de Martin O'Donnell", tCD);
        cd3.agregarCategoria(cBandaSonora); cd3.agregarCategoria(cXbox);

        // Agregar todos los ítems a la lista
        items.add(g1);  items.add(g2);  items.add(g3);  items.add(g4);  items.add(g5);
        items.add(g6);  items.add(g7);  items.add(g8);  items.add(g9);  items.add(g10);
        items.add(c1);  items.add(c2);  items.add(c3);  items.add(c4);  items.add(c5);
        items.add(ct1); items.add(ct2); items.add(ct3); items.add(ct4);
        items.add(p1);  items.add(p2);  items.add(p3);  items.add(p4);  items.add(p5);
        items.add(cd1); items.add(cd2); items.add(cd3);


        // Usuarios
        Usuario u1 = new Usuario("Lucas Alvarez", "8585-3290", "Lucas89@mail.com");
        Usuario u2 = new Usuario("Kristel Munoz", "7324-2654", "Kristel1@mail.com");
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