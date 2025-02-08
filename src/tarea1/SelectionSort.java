package tarea1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class SelectionSort implements Runnable {  // Clase renombrada a Selection

    private int[] arrayOriginal;
    private Cronometro cronometro;
    private DefaultTableModel tableModel;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};

    public SelectionSort(int[] arrayOriginal, DefaultTableModel tableModel) {  // Constructor renombrado
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
            selectionSort(arrayCopia);  // Llamada a Selection Sort
            cronometro.detener();

            System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
            mostrarTiempoTranscurrido();
            cronometro.almacenarTiempo();
            guardarArrayEnArchivo("numeros/ordenamiento_Selection_" + tamaño + ".txt", arrayCopia);  // Nombre de archivo actualizado

            // Actualizar la tabla (los índices de columna se mantienen según lo requerido)
            switch (tamaño) {
                case 100_000:
                    updateTable(cronometro.obtenerTiempoTranscurrido(), 0, 8);
                    break;
                case 1_000_000:
                    updateTable(cronometro.obtenerTiempoTranscurrido(), 1, 8);
                    break;
                case 3_000_000:
                    updateTable(cronometro.obtenerTiempoTranscurrido(), 2, 8);
                    break;
                case 5_000_000:
                    updateTable(cronometro.obtenerTiempoTranscurrido(), 3, 8);
                    break;
                case 8_000_000:
                    updateTable(cronometro.obtenerTiempoTranscurrido(), 4, 8);
                    break;
                case 10_000_000:
                    updateTable(cronometro.obtenerTiempoTranscurrido(), 5, 8);
                    break;
            }

            reset();
        }
        System.out.println("Todos los incrementos han sido procesados.");
    }

    // Método de ordenamiento Selection Sort
    private void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            // Intercambiar el elemento mínimo con la posición actual
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    // Resto de los métodos se mantienen igual...
    // (mostrarTiempoTranscurrido, reset, liberarMemoria, guardarArrayEnArchivo, updateTable)
    
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