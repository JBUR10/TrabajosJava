package TrabajosJava.Nomina;

import java.util.Scanner;

public class Nomina {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = 0;
        do {
            System.out.print("Cantidad de trabajadores (30-300): ");
            n = sc.nextInt();
        } while (n < 30 || n > 300);

        String[] nombre = new String[n];
        int[] codigo = new int[n];
        char[] estadoCivil = new char[n];
        char[] sexo = new char[n];
        double[] valorHora = new double[n];
        int[] horasMes = new int[n];
        double[] salario = new double[n];
        double[] retSS = new double[n];
        double[] retImp = new double[n];
        double[] neto = new double[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nTrabajador " + (i + 1) + "");

            System.out.print("  Nombre: ");
            nombre[i] = sc.next();

            // Validar código único
            boolean repetido;
            do {
                repetido = false;
                System.out.print("  Código: ");
                codigo[i] = sc.nextInt();

                for (int j = 0; j < i; j++) {
                    if (codigo[j] == codigo[i]) {
                        System.out.println("Ese código ya existe. Intenta otro.");
                        repetido = true;
                        break;
                    }
                }
            } while (repetido);

            System.out.print("  Estado civil (S/C/U/D/V): ");
            estadoCivil[i] = sc.next().toUpperCase().charAt(0);

            System.out.print("  Sexo (F/M): ");
            sexo[i] = sc.next().toUpperCase().charAt(0);

            System.out.print("  Valor por hora: ");
            valorHora[i] = sc.nextDouble();

            System.out.print("  Horas del mes: ");
            horasMes[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            salario[i] = valorHora[i] * horasMes[i];

            if (salario[i] >= 750_000) {
                retImp[i] = salario[i] * 0.05;
                retSS[i] = salario[i] * 0.03;
            } else {
                retImp[i] = 0;
                retSS[i] = salario[i] * 0.02;
            }

            neto[i] = salario[i] - retSS[i] - retImp[i];
        }

        // Menú
        int opcion;
        do {
            System.out.println("\n===== MENÚ NÓMINA =====");
            System.out.println("1. Reportes globales");
            System.out.println("2. Buscar por código (secuencial)");
            System.out.println("3. Ordenar por código");
            System.out.println("4. Buscar por código (binaria)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

            if (opcion == 1) {
                reportes(n, sexo, salario, retSS, neto);
            } else if (opcion == 2) {
                buscarSecuencial(n, codigo, nombre, neto);
            } else if (opcion == 3) {
                ordenar(n, codigo, nombre, estadoCivil, sexo, valorHora, horasMes, salario, retSS, retImp, neto);
            } else if (opcion == 4) {
                buscarBinaria(n, codigo, nombre, neto);
            } else if (opcion != 0) {
                System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // Funciones del menú
    static void reportes(int n, char[] sexo, double[] salario, double[] retSS, double[] neto) {
        int mujeres = 0;
        double sumaSalario = 0, totalSS = 0;
        int iMayor = 0, iMenor = 0;

        for (int i = 0; i < n; i++) {
            if (sexo[i] == 'F') {
                mujeres++;
            }

            sumaSalario += salario[i];
            totalSS += retSS[i];

            if (neto[i] > neto[iMayor]) {
                iMayor = i;
            }
            if (neto[i] < neto[iMenor]) {
                iMenor = i;
            }
        }

        System.out.println("\n--- REPORTES ---");
        System.out.printf("1. Porcentaje de mujeres: %.1f%%%n", (mujeres * 100.0) / n);
        System.out.printf("2. Promedio salario básico: $%,.0f%n", sumaSalario / n);
        System.out.printf("3. Total seguridad social: $%,.0f%n", totalSS);
        System.out.printf("4. Mayor neto → Índice %d  Neto: $%,.0f%n", iMayor, neto[iMayor]);
        System.out.printf("5. Menor neto → Índice %d  Neto: $%,.0f%n", iMenor, neto[iMenor]);

        System.out.println("6. Alerta (neto < SMMLV $1.300.000):");
        boolean hay = false;

        for (int i = 0; i < n; i++) {
            if (neto[i] < 1_300_000) {
                System.out.printf("Índice %d  Neto: $%,.0f%n", i, neto[i]);
                hay = true;
            }
        }

        if (!hay) {
            System.out.println("Ninguno.");
        }
    }

    // Búsqueda secuencial
    static void buscarSecuencial(int n, int[] codigo, String[] nombre, double[] neto) {
        System.out.print("Código a buscar: ");
        int buscar = sc.nextInt();

        for (int i = 0; i < n; i++) {
            if (codigo[i] == buscar) {
                System.out.printf("Hallado en índice %d | %s | Neto: $%,.0f%n",
                        i, nombre[i], neto[i]);
                return;
            }
        }
        System.out.println("No encontrado.");
    }

    // Ciclo burbuja
    static void ordenar(int n, int[] codigo, String[] nombre, char[] ec, char[] sexo,
            double[] vh, int[] hm, double[] sal, double[] rss, double[] ri, double[] neto) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (codigo[j] > codigo[j + 1]) {
                    int ti = codigo[j];
                    codigo[j] = codigo[j + 1];
                    codigo[j + 1] = ti;

                    String ts = nombre[j];
                    nombre[j] = nombre[j + 1];
                    nombre[j + 1] = ts;

                    char tc = ec[j];
                    ec[j] = ec[j + 1];
                    ec[j + 1] = tc;

                    char tx = sexo[j];
                    sexo[j] = sexo[j + 1];
                    sexo[j + 1] = tx;

                    double td = vh[j];
                    vh[j] = vh[j + 1];
                    vh[j + 1] = td;

                    int th = hm[j];
                    hm[j] = hm[j + 1];
                    hm[j + 1] = th;
                    td = sal[j];
                    sal[j] = sal[j + 1];
                    sal[j + 1] = td;
                    td = rss[j];
                    rss[j] = rss[j + 1];
                    rss[j + 1] = td;
                    td = ri[j];
                    ri[j] = ri[j + 1];
                    ri[j + 1] = td;
                    td = neto[j];
                    neto[j] = neto[j + 1];
                    neto[j + 1] = td;
                }
            }
        }
        System.out.println("Arreglos ordenados por código.");
    }

    // Búsqueda binaria
    static void buscarBinaria(int n, int[] codigo, String[] nombre, double[] neto) {
        System.out.print("Código a buscar (binaria): ");
        int buscar = sc.nextInt();

        int bajo = 0, alto = n - 1;
        while (bajo <= alto) {
            int medio = (bajo + alto) / 2;

            if (codigo[medio] == buscar) {
                System.out.printf("Hallado en índice %d | %s | Neto: $%,.0f%n",
                        medio, nombre[medio], neto[medio]);
                return;
            } else if (codigo[medio] < buscar) {
                bajo = medio + 1;
            } else {
                alto = medio - 1;
            }
        }
        System.out.println("No encontrado.");
    }
}