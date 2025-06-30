package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class MainMenuView extends JPanel {

    public MainMenuView(JFrame parent) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));

        JButton btnComprar = new JButton("Registrar Compra");
        JButton btnBuscar = new JButton("Buscar Compras");
        JButton btnSalir = new JButton("Cerrar sesión");

        add(btnComprar);
        add(btnBuscar);
        add(btnSalir);

        btnComprar.addActionListener((ActionEvent e) -> {
            ((MainFrame) parent).cambiarVista(new CompraView(parent));
        });

        btnSalir.addActionListener((ActionEvent e) -> {
            ((MainFrame) parent).cambiarVista(new LoginView(parent));
        });
    }
}
