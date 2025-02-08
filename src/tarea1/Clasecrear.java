package tarea1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Clasecrear implements Runnable {
    private String rutaRelativa;
    private String nombreArchivo;
    private int cantidad;
    public int[] arrayOriginal; // Array público para almacenar los números generados

    // Constructor para inicializar los parámetros
    public Clasecrear(String rutaRelativa, String nombreArchivo, int cantidad) {
        this.rutaRelativa = rutaRelativa;
        this.nombreArchivo = nombreArchivo;
        this.cantidad = cantidad;
        this.arrayOriginal = new int[cantidad]; // Inicializar el array con el tamaño especificado
    }

    @Override
    public void run() {
        // Combinar la ruta relativa con el nombre del archivo
        File archivo = new File(rutaRelativa, nombreArchivo);

        try {
            // Crear el archivo si no existe
            if (archivo.createNewFile()) {
                System.out.println("Archivo creado: " + archivo.getAbsolutePath());
            } else {
                System.out.println("El archivo ya existe. Sobrescribiendo contenido...");
            }

            // Escribir los números aleatorios en el archivo
            FileWriter escritor = new FileWriter(archivo);
            Random random = new Random();

            for (int i = 0; i < cantidad; i++) {
                int numero = random.nextInt(1000000); // Números aleatorios entre 0 y 999,999
                arrayOriginal[i] = numero; // Almacenar el número en el array
                escritor.write(numero + ",\n"); // Escribe el número seguido de una coma y un salto de línea
            }

            escritor.close();
            System.out.println(cantidad + " números aleatorios generados y guardados en el archivo.");

        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear o escribir en el archivo.");
            e.printStackTrace();
        }

        // El hilo se detiene automáticamente al terminar el método run()
        System.out.println("Hilo de generación de números finalizado.");
    }

    // Método para obtener el arrayOriginal
    public int[] getArrayOriginal() {
        return arrayOriginal;
    }
}