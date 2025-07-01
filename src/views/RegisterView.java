package views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterView extends JPanel {

    public RegisterView(JFrame parent, Connection conexion) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ─── Logo + Título ──────────────────────
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel();
        try {
            ImageIcon logoIcon = new ImageIcon("assets/logo.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledLogo));
        } catch (Exception e) {
            System.err.println("No se pudo cargar logo.png");
        }
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));

        JLabel titleLabel = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 50));

        topPanel.add(logoLabel, BorderLayout.NORTH);
        topPanel.add(titleLabel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // ─── Imagen decorativa ──────────────────────
        JLabel decorLabel = new JLabel();
        try {
            ImageIcon decorIcon = new ImageIcon("assets/utp_image.png");
            Image scaledDecor = decorIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            decorLabel.setIcon(new ImageIcon(scaledDecor));
        } catch (Exception e) {
            System.err.println("No se pudo cargar utp_image.png");
        }
        decorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        decorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(decorLabel);

        // ─── Formulario ──────────────────────
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Campos
        JTextField nombres = new JTextField(15);
        JTextField apellidoPaterno = new JTextField(15);
        JTextField apellidoMaterno = new JTextField(15);
        JTextField correo = new JTextField(15);
        JPasswordField clave = new JPasswordField(15);
        JPasswordField repetirClave = new JPasswordField(15);
        JTextField telefono = new JTextField(15); // opcional

        // Añadir al formulario
        formPanel.add(new JLabel("Nombres:"), gbc);
        gbc.gridy++; formPanel.add(nombres, gbc);
        gbc.gridy++; formPanel.add(new JLabel("Apellido Paterno:"), gbc);
        gbc.gridy++; formPanel.add(apellidoPaterno, gbc);
        gbc.gridy++; formPanel.add(new JLabel("Apellido Materno:"), gbc);
        gbc.gridy++; formPanel.add(apellidoMaterno, gbc);
        gbc.gridy++; formPanel.add(new JLabel("Correo:"), gbc);
        gbc.gridy++; formPanel.add(correo, gbc);
        gbc.gridy++; formPanel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridy++; formPanel.add(clave, gbc);
        gbc.gridy++; formPanel.add(new JLabel("Repetir Contraseña:"), gbc);
        gbc.gridy++; formPanel.add(repetirClave, gbc);
        gbc.gridy++; formPanel.add(new JLabel("Número de contacto (opcional):"), gbc);
        gbc.gridy++; formPanel.add(telefono, gbc);

        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ─── Botones ──────────────────────
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton registrar = new JButton("Registrar");
        JButton volver = new JButton("Volver");

        estilizarBoton(registrar, new Color(76, 175, 80)); // Verde
        estilizarBoton(volver, new Color(117, 117, 117));  // Gris

        buttonPanel.add(registrar);
        buttonPanel.add(volver);

        centerPanel.add(buttonPanel);

        // ─── Eventos ──────────────────────
        registrar.addActionListener(e -> {
            String nombre = nombres.getText().trim();
            String apePat = apellidoPaterno.getText().trim();
            String apeMat = apellidoMaterno.getText().trim();
            String email = correo.getText().trim();
            String pass = String.valueOf(clave.getPassword()).trim();
            String repetirPass = String.valueOf(repetirClave.getPassword()).trim();
            String telefonoVal = telefono.getText().trim();

            if (nombre.isEmpty() || apePat.isEmpty() || apeMat.isEmpty() || email.isEmpty() || pass.isEmpty() || repetirPass.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Completa todos los campos obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!pass.equals(repetirPass)) {
                JOptionPane.showMessageDialog(parent, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (registrarUsuario(conexion, email, pass)) {
                JOptionPane.showMessageDialog(parent, "Usuario registrado correctamente.");
                ((MainFrame) parent).cambiarVista(new LoginView(parent));
            } else {
                JOptionPane.showMessageDialog(parent, "Error: El correo ya existe o hubo un problema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        volver.addActionListener(e -> ((MainFrame) parent).cambiarVista(new LoginView(parent)));
    }

    private void estilizarBoton(JButton boton, Color background) {
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        boton.setBackground(background != null ? background : (new Color(66, 133, 244)));
        boton.setForeground(Color.WHITE);
        boton.setPreferredSize(new Dimension(120, 32));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private boolean registrarUsuario(Connection conexion, String correo, String clave) {
        String sql = "INSERT INTO usuarios (correo, clave) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, clave);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
}
