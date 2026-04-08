package Calculadora;

import javax.swing.JFrame;

public class PantallaPrincipal extends JFrame {

    public PantallaPrincipal() {

        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        add(new Panel());

        setVisible(true);
    }

    public static void main(String[] args) {
        new PantallaPrincipal();
    }
}
