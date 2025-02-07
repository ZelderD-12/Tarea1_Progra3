package tarea1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class SelectionSort implements Runnable{
    
     private int[] arrayOriginal;
    private Cronometro cronometro;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};
    
    public SelectionSort(int[] arrayOriginal) {
        if (arrayOriginal.length != 10_000_000) { // Asegurar tamaño exacto
            throw new IllegalArgumentException("El array original debe tener exactamente 10 millones de elementos.");
        }
        this.arrayOriginal = arrayOriginal;
        this.cronometro = new Cronometro();
    }
    
    @Override
    public void run(){
     int[] arreglodeprueba = {5,0,4,7,8,33,6,99,4,70,25,66};
     
    for(int i = 0; i<arreglodeprueba.length; i++){
        int min = i;
        for(int j = i+1; j<arreglodeprueba.length; j++){
            if(arreglodeprueba[min] > arreglodeprueba[j]){
                min = j;
            }
        }
        
        int temp = arreglodeprueba[i];
        arreglodeprueba[i] = arreglodeprueba[min];
        arreglodeprueba[min] = temp;
    }
    
    for(int a = 0; a< arreglodeprueba.length; a++){
        System.out.println(""+ arreglodeprueba[a]);
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
