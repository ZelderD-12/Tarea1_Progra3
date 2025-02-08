package tarea1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Counting implements Runnable {
    private int[] arrayOriginal;
    private Cronometro cronometro;
    private DefaultTableModel tableModel;
    private int[] incrementos = {100_000, 1_000_000, 3_000_000, 5_000_000, 8_000_000, 10_000_000};
    private static final int MAX_RANGE = 1_000_000;

    public Counting(int[] arrayOriginal, DefaultTableModel tableModel) {
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
            countingSortOptimizado(arrayCopia);
            cronometro.detener();

            System.out.println("Array ordenado (primeros 10 elementos): " + Arrays.toString(Arrays.copyOf(arrayCopia, 10)));
            mostrarTiempoTranscurrido();
                           // Almacenar el tiempo transcurrido antes de reiniciar
            cronometro.almacenarTiempo();
            
            guardarArrayEnArchivo("numeros/ordenamiento_Counting_" + tamaño + ".txt", arrayCopia);
            
            // Actualizar la tabla 
            switch (tamaño) {
                case 100_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 0, 3);  
                    break;
                case 1_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 1, 3);  
                    break;
                case 3_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 2, 3);  
                    break;
                case 5_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 3, 3);  
                    break;
                case 8_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 4, 3);  
                    break;
                case 10_000_000:
                   updateTable(cronometro.obtenerTiempoTranscurrido(), 5, 3);  
                    break;
            }
            reset();
        }
        System.out.println("Todos los incrementos han sido procesados.");
    }

    public void mostrarTiempoTranscurrido() {
        System.out.println("Tiempo transcurrido: " + cronometro.obtenerTiempoTranscurrido() + " ms");
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

    // Método Counting Sort optimizado
    private void countingSortOptimizado(int[] array) {
        int min = array[0], max = array[0];
        for (int num : array) {
            if (num < min) min = num;
            if (num > max) max = num;
        }

        int offset = -min;
        int range = max - min + 1;
        int numSubRanges = (int) Math.ceil((double) range / MAX_RANGE);

        int[] output = new int[array.length];
        for (int i = 0; i < numSubRanges; i++) {
            int subRangeMin = i * MAX_RANGE;
            int subRangeMax = Math.min(subRangeMin + MAX_RANGE - 1, range - 1);
            procesarSubRango(array, output, subRangeMin, subRangeMax, offset);
        }
    }

    private void procesarSubRango(int[] array, int[] output, int subRangeMin, int subRangeMax, int offset) {
        int subRangeSize = subRangeMax - subRangeMin + 1;
        int[] count = new int[subRangeSize + 1];

        // Contar frecuencias
        for (int num : array) {
            int adjusted = num + offset;
            if (adjusted >= subRangeMin && adjusted <= subRangeMax) {
                count[adjusted - subRangeMin + 1]++;
            }
        }

        // Acumular frecuencias
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Ordenar elementos del subrango
        for (int i = array.length - 1; i >= 0; i--) {
            int adjusted = array[i] + offset;
            if (adjusted >= subRangeMin && adjusted <= subRangeMax) {
                output[count[adjusted - subRangeMin]++] = array[i];
            }
        }

        // Copiar elementos ordenados al array original
        int start = count[0];
        for (int i = 0; i < count.length - 1; i++) {
            int end = count[i];
            while (start < end) {
                array[start++] = output[i];
            }
        }
    }
       private void updateTable(long tiempo, int fila, int columna) {
    SwingUtilities.invokeLater(() -> {
        // Asegúrate de que la fila y la columna especificada estén dentro de los límites de la tabla
        if (fila < tableModel.getRowCount() && columna < tableModel.getColumnCount()) {
            // Establecer el tiempo transcurrido en la columna especificada y la fila especificada
            tableModel.setValueAt(tiempo + " ms", fila, columna);
        } else {
            System.err.println("Fila o columna fuera de los límites de la tabla.");
        }
    });
}
}