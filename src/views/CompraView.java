package views;

import javax.swing.*;
import java.awt.*;

public class CompraView extends JPanel {

    public CompraView(JFrame parent) {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Registro de Compra", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        JTextArea area = new JTextArea("Formulario de registro de compra");
        add(area, BorderLayout.CENTER);
    }
}
