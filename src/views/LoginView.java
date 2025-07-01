package views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginView extends JPanel {

    private Connection conexion;

    public LoginView(JFrame parent) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ─── Panel superior con logo y título ──────────────────────────────
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel();
        ImageIcon icon = new ImageIcon("assets/logo.png"); // Ruta relativa correcta
        Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JLabel titleLabel = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 50, 50));

        topPanel.add(logoLabel, BorderLayout.NORTH);
        topPanel.add(titleLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // ─── Imagen decorativa sobre el formulario ──────────────────────────────
        JLabel decorLabel = new JLabel();
        try {
            ImageIcon decorIcon = new ImageIcon("assets/utp_image.png");
            Image scaledDecor = decorIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            decorLabel.setIcon(new ImageIcon(scaledDecor));
            decorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            decorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        } catch (Exception e) {
            System.err.println("No se pudo cargar decorativa.png");
        }
        add(decorLabel, BorderLayout.WEST); // Si prefieres arriba del formulario directamente, ver más abajo 👇

        // ─── Panel intermedio para contener decorLabel y el formulario ─────
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(decorLabel);

        // ─── Formulario ───────────────────────────────────────────────
        JPanel form = new JPanel();
        form.setLayout(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel correoLabel = new JLabel("Correo:");
        JTextField correo = new JTextField(15);
        correo.setPreferredSize(new Dimension(200, 28));

        JLabel claveLabel = new JLabel("Contraseña:");
        JPasswordField clave = new JPasswordField(15);
        clave.setPreferredSize(new Dimension(200, 28));

            

        gbc.anchor = GridBagConstraints.LINE_START;
        form.add(correoLabel, gbc);
        gbc.gridy++;
        form.add(correo, gbc);
        gbc.gridy++;
        form.add(claveLabel, gbc);
        gbc.gridy++;
        form.add(clave, gbc);
        

        centerPanel.add(form);
        add(centerPanel, BorderLayout.CENTER);

        // ─── Botones ───────────────────────────────────────────────
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        ImageIcon loginIcon = new ImageIcon("assets/login_icon.png");
        Image scaledLoginIcon = loginIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        JButton login = new JButton("Ingresar");
        JButton registrar = new JButton("Registrarse");

        estilizarBoton(login);
        estilizarBoton(registrar);

        buttonPanel.add(login);
        buttonPanel.add(registrar);
        buttonPanel.add(new JLabel(new ImageIcon(scaledLoginIcon)));

        add(buttonPanel, BorderLayout.SOUTH);

        // ─── Eventos ───────────────────────────────────────────────
        login.addActionListener(e -> {
            String user = correo.getText();
            String pass = String.valueOf(clave.getPassword());

            if (autenticarUsuario(user, pass)) {
                JOptionPane.showMessageDialog(parent, "Inicio de sesión exitoso.");
                ((MainFrame) parent).cambiarVista(new MainMenuView(parent));
            } else {
                JOptionPane.showMessageDialog(parent, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registrar.addActionListener(e -> {
            ((MainFrame) parent).cambiarVista(new RegisterView(parent, conexion));
        });
    }

    private void estilizarBoton(JButton boton) {
        estilizarBoton(boton, null);
    }

    private void estilizarBoton(JButton boton, Color background) {
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        boton.setForeground(Color.WHITE);
        boton.setPreferredSize(new Dimension(110, 30));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (background != null) {
            boton.setBackground(background);
        } else {
            boton.setBackground(new Color(66, 133, 244)); 
        }
    }

    private boolean autenticarUsuario(String correo, String clave) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND clave = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, clave);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
