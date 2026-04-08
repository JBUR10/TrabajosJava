package EjMatrices;

import java.util.Scanner;

public class OpMatrices {
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        int[][] A = new int[3][3];
        int[][] B = new int[3][3];
        int[][] suma = new int[3][3];
        int[][] resta = new int[3][3];

        System.out.println("Ingrese los valores de la matriz A:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                A[i][j] = teclado.nextInt();
            }
        }

        System.out.println("Ingrese los valores de la matriz B:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                B[i][j] = teclado.nextInt();
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                suma[i][j] = A[i][j] + B[i][j];
                resta[i][j] = A[i][j] - B[i][j];
            }
        }

        System.out.println("\nSuma de matrices:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(suma[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nResta de matrices:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(resta[i][j] + " ");
            }
            System.out.println();
        }
    }
} 
