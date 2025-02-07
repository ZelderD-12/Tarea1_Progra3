/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea1;

/**
 *
 * @author ninet
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Counting implements Runnable {
    private int[] arrayOriginal;
    private Cronometro cronometro;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};
    private static final int MAX_RANGE = 1_000_000; // Tamaño máximo del subrango

    public Counting(int[] arrayOriginal) {
        if (arrayOriginal.length != 10_000_000) {
            throw new IllegalArgumentException("El array original debe tener exactamente 10 millones de elementos.");
        }
        this.arrayOriginal = arrayOriginal;
        this.cronometro = new Cronometro();
    }

    @Override
    public void run() {
        iniciarOrdenamiento();
    }

    public void iniciarOrdenamiento() {
        for (int tamaño : incrementos) {
            int[] arrayCopia = Arrays.copyOf(arrayOriginal, tamaño);
            System.out.println("Preparando para ordenar " + tamaño + " elementos...");

            cronometro.iniciar();
            countingSortWithRanges(arrayCopia); // Usamos el método countingSortWithRanges
            cronometro.detener();

            System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
            mostrarTiempoTranscurrido();
            guardarArrayEnArchivo("numeros/ordenamiento_Counting_" + tamaño + ".txt", arrayCopia);

            reset(); // Resetear cronometro y liberar memoria
        }
        System.out.println("Todos los incrementos han sido procesados.");
    }

    public void mostrarTiempoTranscurrido() {
        System.out.println("Tiempo transcurrido: " + cronometro.obtenerTiempoTranscurrido() + " ms");
    }

    public void reset() {
        cronometro.resetear();
        liberarMemoria();
        System.out.println("Estado de la clase reseteado.");
    }

    private void liberarMemoria() {
        System.gc();
        System.out.println("Memoria liberada.");
    }

    public void guardarArrayEnArchivo(String rutaArchivo, int[] array) {
        File carpeta = new File("numeros");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            for (int num : array) {
                writer.write(num + "\n");
            }
            System.out.println("Array ordenado guardado en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // Método Counting Sort optimizado para números negativos y grandes cantidades de datos
    private void countingSortWithRanges(int[] array) {
        int max = Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
        int min = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);

        // Ajustar valores para que sean positivos
        int offset = -min;
        int[] adjustedArray = Arrays.stream(array).map(num -> num + offset).toArray();

        // Ordenar por subrangos
        int numSubRanges = (int) Math.ceil((double) (max - min + 1) / MAX_RANGE);
        for (int i = 0; i < numSubRanges; i++) {
            int subRangeMin = i * MAX_RANGE;
            int subRangeMax = Math.min(subRangeMin + MAX_RANGE - 1, max - min);
            countingSortSubRange(adjustedArray, subRangeMin, subRangeMax);
        }

        // Revertir el ajuste
        System.arraycopy(Arrays.stream(adjustedArray).map(num -> num - offset).toArray(), 0, array, 0, array.length);
    }

    private void countingSortSubRange(int[] array, int subRangeMin, int subRangeMax) {
        int subRangeSize = subRangeMax - subRangeMin + 1;
        if (subRangeSize <= 0) return;

        int[] count = new int[subRangeSize];
        int[] output = new int[array.length];

        // Contar la frecuencia de cada elemento en el subrango
        for (int num : array) {
            if (num >= subRangeMin && num <= subRangeMax) {
                count[num - subRangeMin]++;
            }
        }

        // Acumular las frecuencias
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Construir el array de salida
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] >= subRangeMin && array[i] <= subRangeMax) {
                output[count[array[i] - subRangeMin] - 1] = array[i];
                count[array[i] - subRangeMin]--;
            }
        }

        // Copiar los elementos ordenados al array original
        for (int i = 0, j = 0; i < array.length; i++) {
            if (output[i] != 0) {
                array[j++] = output[i];
            }
        }
    }
}
