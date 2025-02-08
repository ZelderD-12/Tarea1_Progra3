package tarea1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class QuickSort implements Runnable {  // Implementa Runnable para ejecutar en un hilo

    private int[] arrayOriginal;
    private Cronometro cronometro;
    private DefaultTableModel tableModel;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};

    public QuickSort(int[] arrayOriginal , DefaultTableModel tableModel) {
        if (arrayOriginal.length != 10_000_000) { // Asegurar tamaño exacto
            throw new IllegalArgumentException("El array original debe tener exactamente 10 millones de elementos.");
        }
        this.arrayOriginal = arrayOriginal;
        this.cronometro = new Cronometro();    
        this.tableModel = tableModel;
    }

    @Override
    public void run() {  // Método run() que se ejecutará en el hilo
        iniciarOrdenamiento();
    }

    public void iniciarOrdenamiento() {
        for (int tamaño : incrementos) {
            int[] arrayCopia = Arrays.copyOf(arrayOriginal, tamaño);
            System.out.println("Preparando para ordenar " + tamaño + " elementos...");

            cronometro.iniciar();
            quickSort(arrayCopia, 0, arrayCopia.length - 1);
            cronometro.detener();

            System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
            mostrarTiempoTranscurrido();
               // Almacenar el tiempo transcurrido antes de reiniciar
            cronometro.almacenarTiempo();
            guardarArrayEnArchivo("numeros/ordenamiento_QuickSort_" + tamaño + ".txt", arrayCopia);
         

            // Actualizar la tabla 
            switch (tamaño) {
                case 100_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 0, 4);  
                    break;
                case 1_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 1, 4);  
                    break;
                case 3_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 2, 4);  
                    break;
                case 5_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 3, 4);  
                    break;
                case 8_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 4, 4);  
                    break;
                case 10_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 5, 4);  
                    break;
            }

         
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

    private void quickSort(int[] array, int inicio, int fin) {
        if (inicio < fin) {
            int pivote = particion(array, inicio, fin);
            quickSort(array, inicio, pivote - 1);
            quickSort(array, pivote + 1, fin);
        }
    }

    private int particion(int[] array, int inicio, int fin) {
        int pivote = array[fin];
        int i = inicio - 1;
        for (int j = inicio; j < fin; j++) {
            if (array[j] <= pivote) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[fin];
        array[fin] = temp;
        return i + 1;
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
