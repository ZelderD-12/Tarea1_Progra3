/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author ninet
 */
public class Bubble implements Runnable {
   private int[] arrayOriginal;
   private Cronometro cronometro;
   private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};

   public Bubble(int[] arrayOriginal) {
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
           bubbleSort(arrayCopia);
           cronometro.detener();

           System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
           mostrarTiempoTranscurrido();
           guardarArrayEnArchivo("numeros/ordenamiento_Bubble_" + tamaño + ".txt", arrayCopia);

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

   private void bubbleSort(int[] array) {
       int n = array.length;
       for (int i = 0; i < n - 1; i++) {
           for (int j = 0; j < n - i - 1; j++) {
               if (array[j] > array[j + 1]) {
                   int temp = array[j];
                   array[j] = array[j + 1];
                   array[j + 1] = temp;
               }
           }
       }
   }
}