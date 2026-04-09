package JueguitoSerpiente;

import javax.swing.*;

public class PantallaPrincipal extends JFrame {

    public PantallaPrincipal() {
        setTitle("La serpiente del jueguito del nokia");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); 
        add(new Panel());

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public static void main(String[] args) {
        new PantallaPrincipal();
    }
    

    
}