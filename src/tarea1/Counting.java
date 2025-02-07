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

   public Counting(int[] arrayOriginal) {
       if (arrayOriginal.length != 10_000_000) { // Asegurar tamaño exacto
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
           countingSort(arrayCopia);
           cronometro.detener();

           System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
           mostrarTiempoTranscurrido();
           guardarArrayEnArchivo("numeros/ordenamiento_Counting_" + tamaño + ".txt", arrayCopia);

           reset(); // Resetear cronometro y liberar memoria
       }
       System.out.println("Todos los incrementos han sido procesados.");
   }

   public void mostrarTiempoTranscurrido() {
       cronometro.mostrarTiempoTranscurrido();
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

   private void countingSort(int[] array) {
       int max = Arrays.stream(array).max().orElse(Integer.MAX_VALUE);
       int min = Arrays.stream(array).min().orElse(Integer.MIN_VALUE);
       int range = max - min + 1;

       int[] count = new int[range];
       int[] output = new int[array.length];

       for (int num : array) {
           count[num - min]++;
       }

       for (int i = 1; i < count.length; i++) {
           count[i] += count[i - 1];
       }

       for (int i = array.length - 1; i >= 0; i--) {
           output[count[array[i] - min] - 1] = array[i];
           count[array[i] - min]--;
       }

       System.arraycopy(output, 0, array, 0, array.length);
   }
}

