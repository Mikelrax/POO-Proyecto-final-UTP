package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.MainMenuView;

public class LoginView extends JPanel {

    public LoginView(JFrame parent) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
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

        JButton login = new JButton("Ingresar");
        form.add(new JLabel());
        form.add(login);

        add(form, BorderLayout.CENTER);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // realizar una peticion a la db
                ((MainFrame) parent).cambiarVista(new MainMenuView(parent));
            }
        });
    }
}
