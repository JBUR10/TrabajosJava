package EjMatrices;

public class matriz {
    public static void main(String[] args) {

        int[][] matriz = new int[3][3];

        int numero = 1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matriz[i][j] = numero;
                numero++;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }

    }
}