package TrabajosJava.Nomina;

import java.util.Scanner;

public class Obrero {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int n = 0;
        do {
            System.out.print("Cantidad de obreros (50-200): ");
            n = sc.nextInt();
        } while (n < 50 || n > 200);

        String[] nombre = new String[n];
        int[] codigo = new int[n];
        int[] lun = new int[n];
        int[] mar = new int[n];
        int[] mie = new int[n];
        int[] jue = new int[n];
        int[] vie = new int[n];
        int[] sab = new int[n];
        int[] dom = new int[n];

        double[] totalHoras = new double[n];
        double[] horasExtras = new double[n];
        double[] pago = new double[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Obrero " + (i + 1) + " ---");

            System.out.print("  Nombre: ");
            nombre[i] = sc.next();

            boolean repetido;
            do {
                repetido = false;
                System.out.print("  Código: ");
                codigo[i] = sc.nextInt();
                for (int j = 0; j < i; j++) {
                    if (codigo[j] == codigo[i]) {
                        System.out.println("  ⚠ Código repetido. Ingresa otro.");
                        repetido = true;
                        break;
                    }
                }
            } while (repetido);

            System.out.print("  Horas Lun: ");
            lun[i] = sc.nextInt();
            System.out.print("  Horas Mar: ");
            mar[i] = sc.nextInt();
            System.out.print("  Horas Mie: ");
            mie[i] = sc.nextInt();
            System.out.print("  Horas Jue: ");
            jue[i] = sc.nextInt();
            System.out.print("  Horas Vie: ");
            vie[i] = sc.nextInt();
            System.out.print("  Horas Sab: ");
            sab[i] = sc.nextInt();
            System.out.print("  Horas Dom: ");
            dom[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            totalHoras[i] = lun[i] + mar[i] + mie[i] + jue[i] + vie[i] + sab[i] + dom[i];

            if (totalHoras[i] <= 40) {
                pago[i] = totalHoras[i] * 500;
                horasExtras[i] = 0;
            } else {
                horasExtras[i] = totalHoras[i] - 40;
                pago[i] = (40 * 500) + (horasExtras[i] * 700);
            }
        }

        // Menú
        int opcion;
        do {
            System.out.println("\n===== MENÚ OBREROS =====");
            System.out.println("1. Reportes globales");
            System.out.println("2. Buscar por código (secuencial)");
            System.out.println("3. Ordenar por código");
            System.out.println("4. Buscar por código (binaria)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

            if (opcion == 1) {
                reportes(n, pago, horasExtras, lun, mar, mie, jue, vie, sab, dom);
            } else if (opcion == 2) {
                buscarSecuencial(n, codigo, nombre, pago);
            } else if (opcion == 3) {
                ordenar(n, codigo, nombre, lun, mar, mie, jue, vie, sab, dom,
                        totalHoras, horasExtras, pago);
            } else if (opcion == 4) {
                buscarBinaria(n, codigo, nombre, pago);
            } else if (opcion != 0) {
                System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    static void reportes(int n, double[] pago, double[] horasExtras,
            int[] lun, int[] mar, int[] mie, int[] jue,
            int[] vie, int[] sab, int[] dom) {

        double totalPagado = 0, sumaExtras = 0;
        int iMasExtras = 0;
        double[] totalDia = new double[7];

        for (int i = 0; i < n; i++) {
            totalPagado += pago[i];
            sumaExtras += horasExtras[i];
            if (horasExtras[i] > horasExtras[iMasExtras]) {
                iMasExtras = i;
            }

            totalDia[0] += lun[i];
            totalDia[1] += mar[i];
            totalDia[2] += mie[i];
            totalDia[3] += jue[i];
            totalDia[4] += vie[i];
            totalDia[5] += sab[i];
            totalDia[6] += dom[i];
        }

        System.out.println("\n--- REPORTES ---");
        System.out.printf("1. Total pagado empresa     : $%,.0f%n", totalPagado);
        System.out.printf("2. Promedio horas extras    : %.2f%n", sumaExtras / n);
        System.out.printf("3. Obrero más extras        : índice %d (%,.0f hrs)%n",
                iMasExtras, horasExtras[iMasExtras]);

        String[] nombreDia = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" };
        int diaMax = 0;
        for (int d = 1; d < 7; d++) {
            if (totalDia[d] > totalDia[diaMax]) {
                diaMax = d;
            }
        }
        System.out.printf("4. Día con más horas: %s (%.0f hrs en total)%n",
                nombreDia[diaMax], totalDia[diaMax]);
    }

    static void buscarSecuencial(int n, int[] codigo, String[] nombre, double[] pago) {
        System.out.print("Código a buscar: ");
        int buscar = sc.nextInt();
        for (int i = 0; i < n; i++) {
            if (codigo[i] == buscar) {
                System.out.printf("Índice %d | %s | Pago: $%,.0f%n", i, nombre[i], pago[i]);
                return;
            }
        }
        System.out.println("No encontrado.");
    }

    // Ciclo burbuja
    static void ordenar(int n, int[] codigo, String[] nombre,
            int[] lun, int[] mar, int[] mie, int[] jue, int[] vie, int[] sab, int[] dom,
            double[] totalHoras, double[] horasExtras, double[] pago) {

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (codigo[j] > codigo[j + 1]) {
                    int ti = codigo[j];
                    codigo[j] = codigo[j + 1];
                    codigo[j + 1] = ti;

                    String ts = nombre[j];
                    nombre[j] = nombre[j + 1];
                    nombre[j + 1] = ts;

                    ti = lun[j];
                    lun[j] = lun[j + 1];
                    lun[j + 1] = ti;
                    ti = mar[j];
                    mar[j] = mar[j + 1];
                    mar[j + 1] = ti;
                    ti = mie[j];
                    mie[j] = mie[j + 1];
                    mie[j + 1] = ti;
                    ti = jue[j];
                    jue[j] = jue[j + 1];
                    jue[j + 1] = ti;
                    ti = vie[j];
                    vie[j] = vie[j + 1];
                    vie[j + 1] = ti;
                    ti = sab[j];
                    sab[j] = sab[j + 1];
                    sab[j + 1] = ti;
                    ti = dom[j];
                    dom[j] = dom[j + 1];
                    dom[j + 1] = ti;

                    double td = totalHoras[j];
                    totalHoras[j] = totalHoras[j + 1];
                    totalHoras[j + 1] = td;
                    td = horasExtras[j];
                    horasExtras[j] = horasExtras[j + 1];
                    horasExtras[j + 1] = td;
                    td = pago[j];
                    pago[j] = pago[j + 1];
                    pago[j + 1] = td;
                }
            }
        }
        System.out.println("Ordenado por código.");
    }

    static void buscarBinaria(int n, int[] codigo, String[] nombre, double[] pago) {
        System.out.print("Código a buscar (binaria): ");
        int buscar = sc.nextInt();
        int bajo = 0, alto = n - 1;
        while (bajo <= alto) {
            int medio = (bajo + alto) / 2;

            if (codigo[medio] == buscar) {
                System.out.printf("Índice %d | %s | Pago: $%,.0f%n", medio, nombre[medio], pago[medio]);
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