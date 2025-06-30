package views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import db.ConexionSQLite;

public class LoginView extends JPanel {

    private Connection conexion;

    public LoginView(JFrame parent) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JTextField correo = new JTextField();
        JPasswordField clave = new JPasswordField();

        form.add(new JLabel("Correo:"));
        form.add(correo);
        form.add(new JLabel("Contraseña:"));
        form.add(clave);

        JButton login = new JButton("Ingresar");
        JButton registrar = new JButton("Registrarse");

        form.add(login);
        form.add(registrar);

        add(form, BorderLayout.CENTER);

        conexion = ConexionSQLite.conectar();

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
