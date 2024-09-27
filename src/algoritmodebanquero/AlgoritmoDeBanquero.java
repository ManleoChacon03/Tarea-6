/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmodebanquero;

import java.util.Scanner;

public class AlgoritmoDeBanquero {

    private int n; // Número de procesos
    private int m; // Número de recursos
    private int[][] max; // Matriz Max: cantidad máxima de cada recurso que cada proceso puede solicitar
    private int[][] allocated; // Matriz Asignada: recursos actualmente asignados a cada proceso
    private int[][] need; // Matriz Need: recursos restantes que necesita cada proceso
    private int[] available; // Matriz Available: cantidad disponible de cada recurso
    private int[] safeSequence; // Secuencia segura de procesos
    private boolean[] finished; // Indicador de si el proceso ha terminado

    public AlgoritmoDeBanquero(int n, int m) {
        this.n = n;
        this.m = m;
        max = new int[n][m];
        allocated = new int[n][m];
        need = new int[n][m];
        available = new int[m];
        safeSequence = new int[n];
        finished = new boolean[n];
    }

    public void ingresarDatos() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese la matriz Max (cantidad máxima de cada recurso que cada proceso puede solicitar):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        System.out.println("Ingrese la matriz Asignada (recursos actualmente asignados a cada proceso):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                allocated[i][j] = sc.nextInt();
            }
        }

        System.out.println("Ingrese la matriz Available (cantidad disponible de cada recurso):");
        for (int i = 0; i < m; i++) {
            available[i] = sc.nextInt();
        }

        // Calcular la matriz Need = Max - Asignada
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
            }
        }
    }

    public boolean verificarEstadoSeguro() {
        int[] work = new int[m]; // Copia de los recursos disponibles
        System.arraycopy(available, 0, work, 0, m);

        int count = 0;

        while (count < n) {
            boolean procesoEncontrado = false;
            for (int i = 0; i < n; i++) {
                if (!finished[i]) {
                    int j;
                    for (j = 0; j < m; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }

                    if (j == m) { // Si se encuentra un proceso
                        for (int k = 0; k < m; k++) {
                            work[k] += allocated[i][k];
                        }
                        safeSequence[count++] = i;
                        finished[i] = true;
                        procesoEncontrado = true;
                    }
                }
            }

            if (!procesoEncontrado) {
                return false; // No hay ningún proceso que pueda ser ejecutado
            }
        }

        return true; // El sistema está en un estado seguro
    }

    public void mostrarSecuenciaSegura() {
        if (verificarEstadoSeguro()) {
            System.out.println("El sistema está en un estado seguro.");
            System.out.print("La secuencia segura es: ");
            for (int i = 0; i < n; i++) {
                System.out.print("P" + safeSequence[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println("El sistema no está en un estado seguro.");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el número de procesos: ");
        int n = sc.nextInt();

        System.out.print("Ingrese el número de tipos de recursos: ");
        int m = sc.nextInt();

        AlgoritmoDeBanquero banquero = new AlgoritmoDeBanquero(n, m);
        banquero.ingresarDatos();
        banquero.mostrarSecuenciaSegura();
    }
}