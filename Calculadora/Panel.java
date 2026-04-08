package Calculadora;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class Panel extends JPanel implements ActionListener {

    JTextField pantalla;

    double num1 = 0;
    double num2 = 0;
    String operador = "";

    public Panel() {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        pantalla = new JTextField();
        pantalla.setFont(new Font("Arial", Font.BOLD, 24));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        pantalla.setBackground(Color.BLACK);
        pantalla.setForeground(Color.WHITE);
        pantalla.setBorder(new LineBorder(Color.decode("#1800ad"),1));

        add(pantalla, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        botones.setLayout(new GridLayout(4,4));
        botones.setBackground(Color.BLACK);

        String[] textoBotones = {
                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "0","C","=","+"
        };

        for(String texto : textoBotones){

            JButton boton = new JButton(texto);
            boton.setFont(new Font("Arial", Font.BOLD, 18));
            boton.setBackground(Color.BLACK);
            boton.setForeground(Color.WHITE);
            boton.setFocusPainted(false);
            boton.setBorder(new LineBorder(Color.decode("#1800ad"),2));

            boton.addActionListener(this);

            botones.add(boton);
        }

        add(botones, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e){

        String comando = e.getActionCommand();

        if(comando.matches("[0-9]")){
            pantalla.setText(pantalla.getText() + comando);
        }

        else if(comando.matches("[+\\-*/]")){
            num1 = Double.parseDouble(pantalla.getText());
            operador = comando;
            pantalla.setText("");
        }

        else if(comando.equals("=")){

            num2 = Double.parseDouble(pantalla.getText());

            double resultado = 0;

            switch(operador){
                case "+": resultado = num1 + num2; break;
                case "-": resultado = num1 - num2; break;
                case "*": resultado = num1 * num2; break;
                case "/": resultado = num1 / num2; break;
            }

            pantalla.setText(String.valueOf(resultado));
        }

        else if(comando.equals("C")){
            pantalla.setText("");
            num1 = 0;
            num2 = 0;
            operador = "";
        }
    }
}