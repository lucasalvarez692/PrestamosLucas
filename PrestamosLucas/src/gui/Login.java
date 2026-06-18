package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Pantalla de inicio de sesión del sistema de préstamos.
 * El usuario debe ingresar "admin" para entrar.
 */
public class Login extends JFrame {

    private static final long serialVersionUID = 1L;

    public Login() {
        setTitle("Login - Sistema de Préstamos");
        setSize(320, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal con espaciado
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Sistema de Préstamos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Campo usuario
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Usuario:"), gbc);
        JTextField txtUser = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUser, gbc);

        // Campo contraseña
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Clave:"), gbc);
        JPasswordField txtPass = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPass, gbc);

        // Botones
        JButton btnIngresar = new JButton("Ingresar");
        JButton btnSalir = new JButton("Salir");

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(btnIngresar, gbc);
        gbc.gridx = 1;
        panel.add(btnSalir, gbc);

        // Al hacer clic en Ingresar, verifica si el usuario es "admin"
        btnIngresar.addActionListener(e -> {
            if (txtUser.getText().trim().equals("admin")) {
                new VentanaPrincipal().setVisible(true);
                dispose(); // cierra el login
            } else {
                JOptionPane.showMessageDialog(this, "Usuario incorrecto.\nUse: admin");
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));

        add(panel);
    }

    // Punto de entrada del programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
