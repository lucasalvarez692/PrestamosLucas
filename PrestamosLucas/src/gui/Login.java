package gui;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;

    public Login() {
        setTitle("Login - Sistema de Préstamos");
        setSize(320, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Sistema de Préstamos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Usuario:"), gbc);
        JTextField txtUser = new JTextField(15);
        gbc.gridx = 1;
        panel.add(txtUser, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Clave:"), gbc);
        JPasswordField txtPass = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(txtPass, gbc);

        JButton btnIngresar = new JButton("Ingresar");
        JButton btnSalir = new JButton("Salir");

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(btnIngresar, gbc);
        gbc.gridx = 1;
        panel.add(btnSalir, gbc);

        btnIngresar.addActionListener(e -> {
            if (txtUser.getText().trim().equals("Lucas")) {
                new VentanaPrincipal().setVisible(true);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Usuario incorrecto.\nUse: Lucas");
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}
