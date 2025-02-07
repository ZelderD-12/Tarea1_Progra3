/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea1;

import java.util.Arrays;

/**
 *
 * @author Usuario
 */
public class QuickSort {

    // Método QuickSort
    public static void ordenar(int[] array, int low, int high) {
        quickSort(array, low, high);
    }

    // Método recursivo para QuickSort
    private static void quickSort(int[] array, int bajo, int alto) {
        while (bajo < alto) { // Usar bucle en vez de recursión infinita
            int indiceParticion = particion(array, bajo, alto);

            // Ordenar la parte más pequeña primero para evitar sobrecarga de pila
            if (indiceParticion - bajo < alto - indiceParticion) {
                quickSort(array, bajo, indiceParticion - 1);
                bajo = indiceParticion + 1;
            } else {
                quickSort(array, indiceParticion + 1, alto);
                alto = indiceParticion - 1;
            }
        }
    }

    // Método para encontrar la partición en QuickSort
    private static int particion(int[] array, int bajo, int alto) {
        int pivote = array[alto]; // Seleccionamos el último elemento como pivote
        int i = (bajo - 1);

        for (int j = bajo; j < alto; j++) {
            if (array[j] <= pivote) {
                i++;
                intercambiar(array, i, j);
            }
        }
        intercambiar(array, i + 1, alto);
        return i + 1;
    }

    // Método para intercambiar dos elementos en el arreglo
    private static void intercambiar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Método para formatear el tiempo en hh:mm:ss:SS
    public static String formatearTiempo(long nanoTime) {
        long millis = nanoTime / 1_000_000;
        long segundos = (millis / 1000) % 60;
        long minutos = (millis / (1000 * 60)) % 60;
        long horas = (millis / (1000 * 60 * 60)) % 24;
        long milisRestantes = millis % 1000;

        return String.format("%02d:%02d:%02d:%03d", horas, minutos, segundos, milisRestantes);
    }
}
