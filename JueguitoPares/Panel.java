package JueguitoPares;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Panel extends JPanel {

    final int TAM_CELDA = 120;

    final int COLUMNAS = 5;
    final int FILAS = 2;

    final int ANCHO = COLUMNAS * TAM_CELDA;
    final int ALTO = FILAS * TAM_CELDA + 40;

    final int MARGEN = 8;

    String[] nombresImagen = {
            "c1.png",
            "c2.png",
            "c3.png",
            "c4.png",
            "c5.png"
    };

    Image[] imagenes = new Image[nombresImagen.length];

    int[] cartas = new int[FILAS * COLUMNAS];

    boolean[] revelada = new boolean[FILAS * COLUMNAS];
    boolean[] emparejada = new boolean[FILAS * COLUMNAS];

    int primera = -1;
    int segunda = -1;

    boolean esperando = false;
    boolean gano = false;
    boolean perdio = false;

    int pares = 0;
    int intentos = 0;
    int vidas = 5;

    public Panel() {

        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(new Color(24,0,173));
        setFocusable(true);

        cargarImagenes();
        iniciarCartas();

        addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {

                if (esperando || gano || perdio) return;

                int col = e.getX() / TAM_CELDA;
                int fila = e.getY() / TAM_CELDA;

                int indice = fila * COLUMNAS + col;

                if (indice < 0 || indice >= cartas.length) return;
                if (revelada[indice] || emparejada[indice]) return;

                revelada[indice] = true;
                repaint();

                if (primera == -1) {

                    primera = indice;

                } else {

                    segunda = indice;
                    intentos++;
                    esperando = true;

                    Timer t = new Timer(1000, ev -> {
                        verificar();
                        esperando = false;
                        repaint();
                    });

                    t.setRepeats(false);
                    t.start();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R)
                    reiniciar();
            }
        });
    }

    void cargarImagenes() {

        for (int i = 0; i < nombresImagen.length; i++) {
            imagenes[i] = new ImageIcon("images/" + nombresImagen[i]).getImage();
        }

    }

    void iniciarCartas() {

        ArrayList<Integer> lista = new ArrayList<>();

        for (int i = 0; i < nombresImagen.length; i++) {
            lista.add(i);
            lista.add(i);
        }

        Collections.shuffle(lista);

        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = lista.get(i);
        }
    }

    void verificar() {

        if (cartas[primera] == cartas[segunda]) {

            emparejada[primera] = true;
            emparejada[segunda] = true;

            pares++;

            if (pares == nombresImagen.length)
                gano = true;

        } else {

            revelada[primera] = false;
            revelada[segunda] = false;

            vidas--;

            if (vidas <= 0)
                perdio = true;
        }

        primera = -1;
        segunda = -1;
    }

    void reiniciar() {

        cartas = new int[FILAS * COLUMNAS];
        revelada = new boolean[FILAS * COLUMNAS];
        emparejada = new boolean[FILAS * COLUMNAS];

        primera = -1;
        segunda = -1;

        esperando = false;
        gano = false;
        perdio = false;

        pares = 0;
        intentos = 0;
        vidas = 5;

        iniciarCartas();
        repaint();
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        for (int fila = 0; fila < FILAS; fila++) {

            for (int col = 0; col < COLUMNAS; col++) {

                dibujarCarta(g, col, fila);

            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));

        g.drawString(
                "Intentos: " + intentos +
                        "   Pares: " + pares + "/" + nombresImagen.length +
                        "   Vidas: " + vidas,
                10, ALTO - 10
        );

        if (gano) mostrarMensaje(g,"GANASTE",Color.WHITE);
        if (perdio) mostrarMensaje(g,"PERDISTE",Color.RED);

    }

    void mostrarMensaje(Graphics g,String texto,Color color){

        g.setColor(new Color(0,0,0,180));
        g.fillRect(0,0,ANCHO,ALTO);

        g.setColor(color);
        g.setFont(new Font("Arial",Font.BOLD,52));

        int w = g.getFontMetrics().stringWidth(texto);

        g.drawString(texto,(ANCHO-w)/2,ALTO/2-20);

        g.setFont(new Font("Arial",Font.PLAIN,22));

        String sub = "Presiona R para reiniciar";

        int sw = g.getFontMetrics().stringWidth(sub);

        g.drawString(sub,(ANCHO-sw)/2,ALTO/2+30);
    }

    void dibujarCarta(Graphics g,int col,int fila){

        int indice = fila * COLUMNAS + col;

        int x = col * TAM_CELDA + MARGEN;
        int y = fila * TAM_CELDA + MARGEN;

        int w = TAM_CELDA - MARGEN * 2;
        int h = TAM_CELDA - MARGEN * 2;

        if (emparejada[indice] || revelada[indice]) {

            g.setColor(Color.WHITE);
            g.fillRoundRect(x,y,w,h,15,15);

            g.setColor(new Color(0,120,255));
            g.drawRoundRect(x,y,w,h,15,15);

            Image img = imagenes[cartas[indice]];

            g.drawImage(img,x+10,y+10,w-20,h-20,this);

        } else {

            g.setColor(new Color(10,10,120));
            g.fillRoundRect(x,y,w,h,15,15);

            g.setColor(new Color(0,120,255));
            g.drawRoundRect(x,y,w,h,15,15);
        }
    }
}