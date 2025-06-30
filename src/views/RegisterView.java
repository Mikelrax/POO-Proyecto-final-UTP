package views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterView extends JPanel {

    public RegisterView(JFrame parent, Connection conexion) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JTextField correo = new JTextField();
        JPasswordField clave = new JPasswordField();

        form.add(new JLabel("Correo:"));
        form.add(correo);
        form.add(new JLabel("Contraseña:"));
        form.add(clave);

        JButton registrar = new JButton("Registrar");
        form.add(new JLabel());
        form.add(registrar);

        add(form, BorderLayout.CENTER);

        registrar.addActionListener(e -> {
            String email = correo.getText();
            String pass = String.valueOf(clave.getPassword());

            if (registrarUsuario(conexion, email, pass)) {
                JOptionPane.showMessageDialog(parent, "Usuario registrado correctamente.");
                ((MainFrame) parent).cambiarVista(new LoginView(parent));
            } else {
                JOptionPane.showMessageDialog(parent, "Error: El correo ya existe o hubo un problema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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
