
package tarea1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class ShellSort extends Thread{
    
     private int[] arrayOriginal;
    private Cronometro cronometro;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};
    
    public ShellSort(int[] arrayOriginal) {
        if (arrayOriginal.length != 10_000_000) { // Asegurar tamaño exacto
            throw new IllegalArgumentException("El array original debe tener exactamente 10 millones de elementos.");
        }
        this.arrayOriginal = arrayOriginal;
        this.cronometro = new Cronometro();
    }
    
    
        @Override
    public void run(){
        int n = arrayOriginal.length;

        // Comienza con un intervalo grande y reduce el intervalo a la mitad en cada iteración
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // Realiza una inserción de elementos usando el intervalo (gap)
            for (int i = gap; i < n; i++) {
                int temp = arrayOriginal[i];
                int j = i;

                // Mueve los elementos arr[0..i-gap] que son mayores que temp
                while (j >= gap && arrayOriginal[j - gap] > temp) {
                    arrayOriginal[j] = arrayOriginal[j - gap];
                    j -= gap;
                }
                arrayOriginal[j] = temp;
            }
        }
    };
    
    
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
}
