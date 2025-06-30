package views;
import javax.swing.*;
import java.net.URL;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Librería Digital UTP");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        URL iconURL = getClass().getResource("/assets/logo.png");
        if (iconURL != null) {
            setIconImage(new ImageIcon(iconURL).getImage());
        } else {
            System.out.println("Logo no encontrado.");
        }

        setContentPane(new LoginView(this));
    }

    public void cambiarVista(JPanel nuevaVista) {
        setContentPane(nuevaVista);
        revalidate();
        repaint();
    }
}
