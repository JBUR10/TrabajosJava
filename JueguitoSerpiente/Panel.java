package JueguitoSerpiente;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int CELL_SIZE = 50;
    private static final int COLS = WIDTH / CELL_SIZE;
    private static final int ROWS = HEIGHT / CELL_SIZE;

    private Timer timer;

    private int[] snakeX = new int[100];
    private int[] snakeY = new int[100];

    private int snakeLength = 5;
    private char direction = 'R';

    private int foodX;
    private int foodY;

    private Random random = new Random();

    private boolean running = true;
    private int score = 0;

    private void checkFood() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeLength++;
            score += 10;
            spawnFood();
        }
    }

    private void checkCollisions() {

        if (snakeX[0] < 0 || snakeX[0] >= WIDTH || snakeY[0] < 0 || snakeY[0] >= HEIGHT) {
            running = false;
        }

        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                running = false;
                break;
            }
        }
    }

    private void spawnFood() {
        foodX = random.nextInt(COLS) * CELL_SIZE;
        foodY = random.nextInt(ROWS) * CELL_SIZE;
    }

    public Panel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        spawnFood();

        int startX = (COLS / 2) * CELL_SIZE;
        int startY = (ROWS / 2) * CELL_SIZE;

        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = startX - (i * CELL_SIZE);
            snakeY[i] = startY;
        }

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {

                int key = e.getKeyCode();

                if (key == KeyEvent.VK_UP && direction != 'D')
                    direction = 'U';
                else if (key == KeyEvent.VK_DOWN && direction != 'U')
                    direction = 'D';
                else if (key == KeyEvent.VK_LEFT && direction != 'R')
                    direction = 'L';
                else if (key == KeyEvent.VK_RIGHT && direction != 'L')
                    direction = 'R';

                if (key == KeyEvent.VK_R)
                    startGame();
            }
        });

        timer = new Timer(200, this);
        timer.start();

        requestFocus();
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.setColor(new Color(40, 70, 120));

        for (int x = 0; x < WIDTH; x += CELL_SIZE) {
            g.drawLine(x, 0, x, HEIGHT);
        }

        for (int y = 0; y < HEIGHT; y += CELL_SIZE) {
            g.drawLine(0, y, WIDTH, y);
        }

        for (int i = 0; i < snakeLength; i++) {

            if (i == 0) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(new Color(0, 0, 139));
            }

            g.fillRect(snakeX[i], snakeY[i], CELL_SIZE, CELL_SIZE);
        }

        g.setColor(Color.RED);
        g.fillOval(foodX + 3, foodY + 3, CELL_SIZE - 6, CELL_SIZE - 6);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Score: " + score, 15, 25);

        if (!running) {

            g.setFont(new Font("Arial", Font.BOLD, 42));
            String msg = "GAME OVER";
            int msgWidth = g.getFontMetrics().stringWidth(msg);

            g.drawString(msg, (WIDTH - msgWidth) / 2, HEIGHT / 2);
        }
    }

    private void startGame() {

        score = 0;
        running = true;
        snakeLength = 5;
        direction = 'R';

        int startX = (COLS / 2) * CELL_SIZE;
        int startY = (COLS / 2) * CELL_SIZE;

        for (int i = 0; i < snakeLength; i++) {
            snakeX[i] = startX - (i * CELL_SIZE);
            snakeY[i] = startY;
        }

        spawnFood();
        repaint();
    }

    private void moveSnake() {

        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {

            case 'U':
                snakeY[0] -= CELL_SIZE;
                break;

            case 'D':
                snakeY[0] += CELL_SIZE;
                break;

            case 'L':
                snakeX[0] -= CELL_SIZE;
                break;

            case 'R':
                snakeX[0] += CELL_SIZE;
                break;
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (running) {
            moveSnake();
            checkFood();
            checkCollisions();
        }

        repaint();
    }
}