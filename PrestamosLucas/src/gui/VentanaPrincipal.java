package gui;

import control.Controladora;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logica.*;

/**
 * Ventana principal del sistema de préstamos.
 * Tiene pestañas para: Usuarios, Ítems, Préstamos, y Catálogos (Tipos/Categorías).
 */
public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    // Referencia a la controladora (toda la lógica pasa por aquí)
    private Controladora control;

    // Modelos de tabla para cada pestaña
    private DefaultTableModel modeloUsuarios;
    private DefaultTableModel modeloItems;
    private DefaultTableModel modeloPrestamos;
    private DefaultTableModel modeloTipos;
    private DefaultTableModel modeloCategorias;

    // Tablas
    private JTable tablaUsuarios;
    private JTable tablaItems;
    private JTable tablaPrestamos;
    private JTable tablaTipos;
    private JTable tablaCategorias;

    public VentanaPrincipal() {
        control = Controladora.getInstance();

        setTitle("Sistema de Préstamos - PrestamosLucas");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pestañas principales
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("👤 Usuarios", panelUsuarios());
        tabs.addTab("📦 Ítems", panelItems());
        tabs.addTab("🔄 Préstamos", panelPrestamos());
        tabs.addTab("🏷️ Tipos y Categorías", panelCatalogos());

        add(tabs);

        // Cargar datos al iniciar
        cargarUsuarios();
        cargarItems();
        cargarPrestamos();
        cargarTipos();
        cargarCategorias();
    }

    // ==================== PANEL USUARIOS ====================

    private JPanel panelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Columnas de la tabla
        String[] cols = {"Nombre", "Teléfono", "Email", "Préstamos Activos"};
        modeloUsuarios = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaUsuarios = new JTable(modeloUsuarios);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar  = new JButton("➕ Agregar");
        JButton btnEditar   = new JButton("✏️ Editar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnRefresh  = new JButton("🔄 Refrescar");

        btnAgregar.addActionListener(e  -> dialogAgregarUsuario());
        btnEditar.addActionListener(e   -> dialogEditarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnRefresh.addActionListener(e  -> cargarUsuarios());

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnRefresh);

        panel.add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private void dialogAgregarUsuario() {
        JTextField txtNombre   = new JTextField(20);
        JTextField txtTelefono = new JTextField(20);
        JTextField txtEmail    = new JTextField(20);

        JPanel form = buildForm(
            new String[]{"Nombre:", "Teléfono:", "Email:"},
            new JComponent[]{txtNombre, txtTelefono, txtEmail}
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Agregar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            if (control.crearUsuario(txtNombre.getText().trim(), txtTelefono.getText().trim(), txtEmail.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Usuario agregado.");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error: datos incompletos.");
            }
        }
    }

    private void dialogEditarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un usuario."); return; }

        Usuario u = control.getUsuarios().get(fila);

        JTextField txtNombre   = new JTextField(u.getNombre(), 20);
        JTextField txtTelefono = new JTextField(u.getTelefono(), 20);
        JTextField txtEmail    = new JTextField(u.getEmail(), 20);

        JPanel form = buildForm(
            new String[]{"Nombre:", "Teléfono:", "Email:"},
            new JComponent[]{txtNombre, txtTelefono, txtEmail}
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Editar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            if (control.modificarUsuario(u, txtNombre.getText().trim(), txtTelefono.getText().trim(), txtEmail.getText().trim())) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado.");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.");
            }
        }
    }

    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un usuario."); return; }

        Usuario u = control.getUsuarios().get(fila);
        int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar a " + u.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            if (control.eliminarUsuario(u)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "No se puede eliminar: tiene préstamos activos.");
            }
        }
    }

    private void cargarUsuarios() {
        modeloUsuarios.setRowCount(0);
        for (Usuario u : control.getUsuarios()) {
            modeloUsuarios.addRow(new Object[]{
                u.getNombre(), u.getTelefono(), u.getEmail(),
                u.getPrestamosActivos().size()
            });
        }
    }

    // ==================== PANEL ÍTEMS ====================

    private JPanel panelItems() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"Código", "Nombre", "Descripción", "Tipo", "Estado"};
        modeloItems = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaItems = new JTable(modeloItems);
        tablaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar  = new JButton("➕ Agregar");
        JButton btnEditar   = new JButton("✏️ Editar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");
        JButton btnRefresh  = new JButton("🔄 Refrescar");

        btnAgregar.addActionListener(e  -> dialogAgregarItem());
        btnEditar.addActionListener(e   -> dialogEditarItem());
        btnEliminar.addActionListener(e -> eliminarItem());
        btnRefresh.addActionListener(e  -> cargarItems());

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnRefresh);

        panel.add(new JScrollPane(tablaItems), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private void dialogAgregarItem() {
        if (control.getTipos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Primero agregue al menos un Tipo en la pestaña de Catálogos.");
            return;
        }
        JTextField txtCodigo = new JTextField(15);
        JTextField txtNombre = new JTextField(15);
        JTextField txtDesc   = new JTextField(15);
        JComboBox<Tipo> cbTipo = new JComboBox<>(control.getTipos().toArray(new Tipo[0]));

        JPanel form = buildForm(
            new String[]{"Código:", "Nombre:", "Descripción:", "Tipo:"},
            new JComponent[]{txtCodigo, txtNombre, txtDesc, cbTipo}
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Agregar Ítem", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            Tipo tipo = (Tipo) cbTipo.getSelectedItem();
            if (control.crearItem(txtCodigo.getText().trim(), txtNombre.getText().trim(), txtDesc.getText().trim(), tipo)) {
                JOptionPane.showMessageDialog(this, "Ítem agregado.");
                cargarItems();
            } else {
                JOptionPane.showMessageDialog(this, "Error: código duplicado o datos vacíos.");
            }
        }
    }

    private void dialogEditarItem() {
        int fila = tablaItems.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un ítem."); return; }

        Item item = control.getItems().get(fila);

        JTextField txtCodigo = new JTextField(item.getCodigo(), 15);
        JTextField txtNombre = new JTextField(item.getNombre(), 15);
        JTextField txtDesc   = new JTextField(item.getDescripcion(), 15);
        JComboBox<Tipo> cbTipo = new JComboBox<>(control.getTipos().toArray(new Tipo[0]));
        cbTipo.setSelectedItem(item.getTipo());

        JPanel form = buildForm(
            new String[]{"Código:", "Nombre:", "Descripción:", "Tipo:"},
            new JComponent[]{txtCodigo, txtNombre, txtDesc, cbTipo}
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Editar Ítem", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            Tipo tipo = (Tipo) cbTipo.getSelectedItem();
            if (control.modificarItem(item, txtCodigo.getText().trim(), txtNombre.getText().trim(), txtDesc.getText().trim(), tipo)) {
                JOptionPane.showMessageDialog(this, "Ítem actualizado.");
                cargarItems();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar.");
            }
        }
    }

    private void eliminarItem() {
        int fila = tablaItems.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un ítem."); return; }

        Item item = control.getItems().get(fila);
        int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar ítem: " + item.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            if (control.eliminarItem(item)) {
                JOptionPane.showMessageDialog(this, "Ítem eliminado.");
                cargarItems();
            } else {
                JOptionPane.showMessageDialog(this, "No se puede eliminar: está actualmente prestado.");
            }
        }
    }

    private void cargarItems() {
        modeloItems.setRowCount(0);
        for (Item i : control.getItems()) {
            modeloItems.addRow(new Object[]{
                i.getCodigo(), i.getNombre(), i.getDescripcion(),
                i.getTipo() != null ? i.getTipo().getNombre() : "-",
                i.estaDisponible() ? "Disponible" : "Prestado"
            });
        }
    }

    // ==================== PANEL PRÉSTAMOS ====================

    private JPanel panelPrestamos() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] cols = {"ID", "Usuario", "Ítems", "Fecha Préstamo", "Estado"};
        modeloPrestamos = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaPrestamos = new JTable(modeloPrestamos);
        tablaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNuevo    = new JButton("➕ Nuevo Préstamo");
        JButton btnDevolver = new JButton("✅ Devolver");
        JButton btnAlerta   = new JButton("🔔 Agregar Alerta");
        JButton btnRefresh  = new JButton("🔄 Refrescar");
        JButton btnReporte  = new JButton("📋 Reporte");

        btnNuevo.addActionListener(e    -> dialogNuevoPrestamo());
        btnDevolver.addActionListener(e -> devolverPrestamo());
        btnAlerta.addActionListener(e   -> dialogAgregarAlerta());
        btnRefresh.addActionListener(e  -> { cargarPrestamos(); cargarItems(); cargarUsuarios(); });
        btnReporte.addActionListener(e  -> mostrarReporte());

        botones.add(btnNuevo);
        botones.add(btnDevolver);
        botones.add(btnAlerta);
        botones.add(btnRefresh);
        botones.add(btnReporte);

        panel.add(new JScrollPane(tablaPrestamos), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private void dialogNuevoPrestamo() {
        List<Usuario> usuarios = control.getUsuarios();
        List<Item> disponibles = control.getItemsDisponibles();

        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados.");
            return;
        }
        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ítems disponibles para prestar.");
            return;
        }

        // Selector de usuario
        JComboBox<Usuario> cbUsuario = new JComboBox<>(usuarios.toArray(new Usuario[0]));

        // Lista de ítems con selección múltiple
        JList<Item> listaItems = new JList<>(disponibles.toArray(new Item[0]));
        listaItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollItems = new JScrollPane(listaItems);
        scrollItems.setPreferredSize(new Dimension(300, 120));

        JPanel form = new JPanel(new GridLayout(4, 1, 5, 5));
        form.add(new JLabel("Seleccione Usuario:"));
        form.add(cbUsuario);
        form.add(new JLabel("Seleccione Ítems (Ctrl+Click para varios):"));
        form.add(scrollItems);

        int ok = JOptionPane.showConfirmDialog(this, form, "Nuevo Préstamo", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            List<Item> seleccionados = listaItems.getSelectedValuesList();
            if (seleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un ítem.");
                return;
            }
            Usuario usuario = (Usuario) cbUsuario.getSelectedItem();
            if (control.hacerPrestamo(usuario, new ArrayList<>(seleccionados))) {
                JOptionPane.showMessageDialog(this, "Préstamo registrado con éxito.");
                cargarPrestamos();
                cargarItems();
                cargarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear préstamo.");
            }
        }
    }

    private void devolverPrestamo() {
        int fila = tablaPrestamos.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un préstamo."); return; }

        Prestamo p = control.getPrestamos().get(fila);
        if (!p.estaActivo()) {
            JOptionPane.showMessageDialog(this, "Este préstamo ya fue devuelto.");
            return;
        }

        int conf = JOptionPane.showConfirmDialog(this, "¿Confirmar devolución del préstamo #" + p.getId() + "?",
                "Devolver", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            if (control.retornarPrestamo(p)) {
                JOptionPane.showMessageDialog(this, "Préstamo devuelto correctamente.");
                cargarPrestamos();
                cargarItems();
                cargarUsuarios();
            }
        }
    }

    private void dialogAgregarAlerta() {
        int fila = tablaPrestamos.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un préstamo."); return; }

        Prestamo p = control.getPrestamos().get(fila);
        if (!p.estaActivo()) {
            JOptionPane.showMessageDialog(this, "No se puede agregar alerta a un préstamo devuelto.");
            return;
        }

        JTextField txtTipo     = new JTextField("Recordatorio", 15);
        JTextField txtDias     = new JTextField("7", 15);
        JTextField txtMensaje  = new JTextField("Devolver préstamo pronto", 15);

        JPanel form = buildForm(
            new String[]{"Tipo:", "Intervalo (días):", "Mensaje:"},
            new JComponent[]{txtTipo, txtDias, txtMensaje}
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Agregar Alerta al Préstamo #" + p.getId(), JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            try {
                int dias = Integer.parseInt(txtDias.getText().trim());
                if (control.agregarAlerta(p, txtTipo.getText().trim(), dias, txtMensaje.getText().trim())) {
                    JOptionPane.showMessageDialog(this, "Alerta agregada.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar alerta.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los días deben ser un número entero.");
            }
        }
    }

    private void mostrarReporte() {
        String reporte = control.reporteUsuarios() + "\n" + control.reporteItems();
        JTextArea area = new JTextArea(reporte, 15, 40);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Reporte del Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cargarPrestamos() {
        modeloPrestamos.setRowCount(0);
        for (Prestamo p : control.getPrestamos()) {
            // Construir lista de nombres de ítems
            StringBuilder items = new StringBuilder();
            for (Item i : p.getItems()) {
                if (items.length() > 0) items.append(", ");
                items.append(i.getNombre());
            }
            modeloPrestamos.addRow(new Object[]{
                p.getId(),
                p.getUsuario().getNombre(),
                items.toString(),
                p.getFechaPrestamo(),
                p.estaActivo() ? "ACTIVO" : "DEVUELTO"
            });
        }
    }

    // ==================== PANEL CATÁLOGOS (TIPOS Y CATEGORÍAS) ====================

    private JPanel panelCatalogos() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(panelTipos());
        panel.add(panelCategorias());
        return panel;
    }

    private JPanel panelTipos() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Tipos de Ítem"));

        String[] cols = {"Nombre", "Descripción"};
        modeloTipos = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaTipos = new JTable(modeloTipos);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar  = new JButton("➕ Agregar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");

        btnAgregar.addActionListener(e  -> dialogAgregarTipo());
        btnEliminar.addActionListener(e -> eliminarTipo());

        botones.add(btnAgregar);
        botones.add(btnEliminar);

        panel.add(new JScrollPane(tablaTipos), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private void dialogAgregarTipo() {
        JTextField txtNombre = new JTextField(15);
        JTextField txtDesc   = new JTextField(15);
        JPanel form = buildForm(new String[]{"Nombre:", "Descripción:"}, new JComponent[]{txtNombre, txtDesc});
        int ok = JOptionPane.showConfirmDialog(this, form, "Agregar Tipo", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            if (control.crearTipo(txtNombre.getText().trim(), txtDesc.getText().trim())) {
                cargarTipos();
            } else {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            }
        }
    }

    private void eliminarTipo() {
        int fila = tablaTipos.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione un tipo."); return; }
        Tipo t = control.getTipos().get(fila);
        if (control.eliminarTipo(t)) cargarTipos();
    }

    private void cargarTipos() {
        modeloTipos.setRowCount(0);
        for (Tipo t : control.getTipos()) {
            modeloTipos.addRow(new Object[]{t.getNombre(), t.getDescripcion()});
        }
    }

    private JPanel panelCategorias() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Categorías"));

        String[] cols = {"Nombre", "Descripción"};
        modeloCategorias = new DefaultTableModel(cols, 0) {
            private static final long serialVersionUID = 1L;
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCategorias = new JTable(modeloCategorias);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAgregar  = new JButton("➕ Agregar");
        JButton btnEliminar = new JButton("🗑️ Eliminar");

        btnAgregar.addActionListener(e  -> dialogAgregarCategoria());
        btnEliminar.addActionListener(e -> eliminarCategoria());

        botones.add(btnAgregar);
        botones.add(btnEliminar);

        panel.add(new JScrollPane(tablaCategorias), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private void dialogAgregarCategoria() {
        JTextField txtNombre = new JTextField(15);
        JTextField txtDesc   = new JTextField(15);
        JPanel form = buildForm(new String[]{"Nombre:", "Descripción:"}, new JComponent[]{txtNombre, txtDesc});
        int ok = JOptionPane.showConfirmDialog(this, form, "Agregar Categoría", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            if (control.crearCategoria(txtNombre.getText().trim(), txtDesc.getText().trim())) {
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
            }
        }
    }

    private void eliminarCategoria() {
        int fila = tablaCategorias.getSelectedRow();
        if (fila == -1) { JOptionPane.showMessageDialog(this, "Seleccione una categoría."); return; }
        Categoria c = control.getCategorias().get(fila);
        if (control.eliminarCategoria(c)) cargarCategorias();
    }

    private void cargarCategorias() {
        modeloCategorias.setRowCount(0);
        for (Categoria c : control.getCategorias()) {
            modeloCategorias.addRow(new Object[]{c.getNombre(), c.getDescripcion()});
        }
    }

    // ==================== MÉTODO AUXILIAR ====================

    /**
     * Construye un formulario genérico con etiquetas y campos.
     * Muy útil para no repetir código en cada diálogo.
     */
    private JPanel buildForm(String[] labels, JComponent[] fields) {
        JPanel form = new JPanel(new GridLayout(labels.length, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        for (int i = 0; i < labels.length; i++) {
            form.add(new JLabel(labels[i]));
            form.add(fields[i]);
        }
        return form;
    }
}
