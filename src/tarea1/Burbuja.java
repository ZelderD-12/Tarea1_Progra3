package tarea1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;


public class Burbuja implements  Runnable{
     private int[] arrayOriginal;
    private Cronometro cronometro;
    private DefaultTableModel tableModel;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};

    public Burbuja(int[] arrayOriginal, DefaultTableModel tableModel) {
        if (arrayOriginal.length != 10_000_000) {
            throw new IllegalArgumentException("El array original debe tener exactamente 10 millones de elementos.");
        }
        this.arrayOriginal = arrayOriginal;
        this.cronometro = new Cronometro();
        this.tableModel = tableModel;
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
            bubbleSortOptimizado(arrayCopia);
            cronometro.detener();

            System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
            mostrarTiempoTranscurrido();
            guardarArrayEnArchivo("numeros/ordenamiento_Bubble_" + tamaño + ".txt", arrayCopia);
            
            // Actualizar la tabla 
            switch (tamaño) {
                case 100_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 0, 2);  
                    break;
                case 1_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 1, 2);  
                    break;
                case 3_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 2, 2);  
                    break;
                case 5_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 3, 2);  
                    break;
                case 8_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 4, 2);  
                    break;
                case 10_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 5, 2);  
                    break;
            }
            reset();
        }
        System.out.println("Todos los incrementos han sido procesados.");
    }

    public void mostrarTiempoTranscurrido() {
        cronometro.mostrarTiempoTranscurrido();
    }

    public void reset() {
        cronometro.resetear();
        System.out.println("Estado de la clase reseteado.");
    }

    public void guardarArrayEnArchivo(String rutaArchivo, int[] array) {
        File carpeta = new File("numeros");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int num : array) {
                writer.write(Integer.toString(num));
                writer.newLine();
            }
            System.out.println("Array ordenado guardado en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private void bubbleSortOptimizado(int[] array) {
        int n = array.length;
        int lastSwap = n - 1;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            int currentLastSwap = 0;
            for (int j = 0; j < lastSwap; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                    currentLastSwap = j;
                }
            }
            lastSwap = currentLastSwap;
            if (!swapped) {
                break;
            }
        }
    }
    
       private void updateTable(long tiempo, int fila, int columna) {
    SwingUtilities.invokeLater(() -> {
        if (fila < tableModel.getRowCount() && columna < tableModel.getColumnCount()) {
            tableModel.setValueAt(tiempo + " ms", fila, columna);
        } else {
            System.err.println("Fila o columna fuera de los límites de la tabla.");
        }
    });
}
}
