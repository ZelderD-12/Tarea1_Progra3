package tarea1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Insert implements Runnable {  // Implementa Runnable para ejecutar en un hilo
    private int[] arrayOriginal;
    private DefaultTableModel tableModel;
    private Cronometro cronometro;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};

    public Insert(int[] arrayOriginal , DefaultTableModel tableModel) {
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
            insertionSort(arrayCopia);
            cronometro.detener();

            System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
            mostrarTiempoTranscurrido();
            guardarArrayEnArchivo("numeros/ordenamiento_Insert_" + tamaño + ".txt", arrayCopia);
            
              // Actualizar la tabla 
            switch (tamaño) {
                case 100_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 0, 5);  
                    break;
                case 1_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 1, 5);  
                    break;
                case 3_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 2, 5);  
                    break;
                case 5_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 3, 5);  
                    break;
                case 8_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 4, 5);  
                    break;
                case 10_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 5, 5);  
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

    private void insertionSort(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;

            // Mover los elementos del array[0..i-1] que son mayores que key
            // a una posición adelante de su posición actual
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
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
