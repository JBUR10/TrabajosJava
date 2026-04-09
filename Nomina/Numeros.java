package TrabajosJava.Nomina;

import java.util.Scanner;

public class Numeros {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int n = 0;
        do {
            System.out.print("Cuántos números? (mínimo 50): ");
            n = sc.nextInt();
        } while (n < 50);

        int k = 0;
        do {
            System.out.print("Valor de K (2-20): ");
            k = sc.nextInt();
        } while (k < 2 || k > 20);

        double[] num = new double[n];
        int[] id = new int[n];
        boolean[] esEntero = new boolean[n];
        boolean[] esMult5 = new boolean[n];
        boolean[] esMultK = new boolean[n];
        boolean[] esPositivo = new boolean[n];
        int[] clasRango = new int[n];

        System.out.println("Ingresa los " + n + " números:");
        for (int i = 0; i < n; i++) {
            System.out.print("  num[" + (i + 1) + "]: ");
            num[i] = sc.nextDouble();
            id[i] = i + 1;

            esEntero[i] = (num[i] == Math.floor(num[i]));
            esPositivo[i] = (num[i] > 0);

            if (esEntero[i]) {
                esMult5[i] = ((int) num[i] % 5 == 0);
                esMultK[i] = ((int) num[i] % k == 0);
            }

            if (num[i] < 0) {
                clasRango[i] = 1;
            } else if (num[i] < 100) {
                clasRango[i] = 2;
            } else if (num[i] < 500) {
                clasRango[i] = 3;
            } else if (num[i] < 1000) {
                clasRango[i] = 4;
            } else {
                clasRango[i] = 5;
            }
        }

        // Menú
        int opcion;
        do {
            System.out.println("\n===== MENÚ NÚMEROS =====");
            System.out.println("1. Ver resultados estadísticos");
            System.out.println("2. Buscar por id (secuencial)");
            System.out.println("3. Ordenar por id");
            System.out.println("4. Buscar por id (binaria)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

            if (opcion == 1) {
                resultados(n, k, num, esMult5, esMultK, esPositivo);
            } else if (opcion == 2) {
                buscarSecuencial(n, id, num);
            } else if (opcion == 3) {
                System.out.println("(Ordenar por id no cambia nada, ya está en orden 1,2,3...)");
            } else if (opcion == 4) {
                buscarBinaria(n, id, num);
            } else if (opcion != 0) {
                System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    static void resultados(int n, int k, double[] num, boolean[] esMult5,
            boolean[] esMultK, boolean[] esPositivo) {

        int contMult5 = 0;
        for (int i = 0; i < n; i++) {
            if (esMult5[i]) {
                contMult5++;
            }
        }

        int[] idxMult5 = new int[contMult5];
        int pos = 0;
        for (int i = 0; i < n; i++) {
            if (esMult5[i]) {
                idxMult5[pos] = i;
                pos++;
            }
        }

        System.out.println("\n1. Múltiplos de 5 (" + contMult5 + " en total):");
        for (int j = 0; j < contMult5; j++) {
            int idx = idxMult5[j];
            System.out.printf("   id=%d  valor=%.0f%n", idx + 1, num[idx]);
        }

        double sumaNoMult5 = 0;
        for (int i = 0; i < n; i++) {
            if (!esMult5[i]) {
                sumaNoMult5 += num[i];
            }
        }
        System.out.printf("2. Suma NO múltiplos de 5: %.2f%n", sumaNoMult5);

        double suma = 0;
        for (int i = 0; i < n; i++) {
            suma += num[i];
        }
        System.out.printf("3. Promedio general: %.2f%n", suma / n);

        int positivos = 0;
        for (int i = 0; i < n; i++)
            if (esPositivo[i]) {
                positivos++;
            }
        System.out.printf("4. Porcentaje positivos: %.1f%%%n", (positivos * 100.0) / n);

        double mayor = num[0], menor = num[0];
        for (int i = 1; i < n; i++) {
            if (num[i] > mayor) {
                mayor = num[i];
            }
            if (num[i] < menor) {
                menor = num[i];
            }
        }
        int vecesMayor = 0, vecesmenor = 0;
        for (int i = 0; i < n; i++) {
            if (num[i] == mayor) {
                vecesMayor++;
            }
            if (num[i] == menor) {
                vecesmenor++;
            }
        }
        System.out.printf("5. Mayor: %.2f (aparece %d veces)%n", mayor, vecesMayor);
        System.out.printf("   Menor: %.2f (aparece %d veces)%n", menor, vecesmenor);

        double segMayor = Double.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (num[i] > segMayor && num[i] < mayor) {
                segMayor = num[i];
            }
        }
        if (segMayor == Double.MIN_VALUE) {
            System.out.println("6. No existe segundo mayor distinto.");
        } else {
            System.out.printf("6. Segundo mayor distinto: %.2f%n", segMayor);
        }
    }

    static void buscarSecuencial(int n, int[] id, double[] num) {
        System.out.print("ID a buscar: ");
        int buscar = sc.nextInt();
        for (int i = 0; i < n; i++) {
            if (id[i] == buscar) {
                System.out.printf("Hallado → id=%d  valor=%.2f%n", id[i], num[i]);
                return;
            }
        }
        System.out.println("No encontrado.");
    }

    static void buscarBinaria(int n, int[] id, double[] num) {
        System.out.print("ID a buscar (binaria): ");
        int buscar = sc.nextInt();

        int bajo = 0, alto = n - 1;
        while (bajo <= alto) {
            int medio = (bajo + alto) / 2;
            if (id[medio] == buscar) {
                System.out.printf("Hallado → id=%d  valor=%.2f%n", id[medio], num[medio]);
                return;
            } else if (id[medio] < buscar) {
                bajo = medio + 1;
            } else {
                alto = medio - 1;
            }
        }
        System.out.println("No encontrado.");
    }
}