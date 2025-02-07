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
public class MergeSort {
    
    // Método Merge Sort
    public static void ordenar(int[] array) {
        if (array.length <= 1) return;
        int mid = array.length / 2;

        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        ordenar(left);
        ordenar(right);

        merge(array, left, right);
    }

    // Método para combinar dos arreglos en Merge Sort
    private static void merge(int[] array, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) array[k++] = left[i++];
        while (j < right.length) array[k++] = right[j++];
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
