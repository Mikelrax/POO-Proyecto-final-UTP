import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("First Screen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            URL iconURL = App.class.getResource("assets/logo.png");
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                frame.setIconImage(icon.getImage());
            } else {
                System.out.println("No se encontró el archivo logo.png");
            }

            JLabel label = new JLabel("Hello World!", SwingConstants.CENTER);
            frame.add(label);
            frame.setTitle("Programación orientada a objetos");
            frame.setVisible(true);
        });
    }
}
