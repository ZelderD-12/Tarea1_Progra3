
package tarea1;
import java.io.File;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ReproducirCancion implements Runnable {
    private List<String> listaCanciones;
    private int indiceActual;

    public ReproducirCancion(List<String> listaCanciones) {
        this.listaCanciones = listaCanciones;
        this.indiceActual = 0;
    }

    @Override
    public void run() {
        while (true) {
            reproducirCancion(listaCanciones.get(indiceActual));
            indiceActual = (indiceActual + 1) % listaCanciones.size();
        }
    }

    private void reproducirCancion(String rutaArchivo) {
        try {
            // Ruta del archivo de audio
            File archivoAudio = new File(rutaArchivo);

            // Crear un AudioInputStream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoAudio);

            // Obtener el clip de audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Reproducir la canción
            clip.start();

            // Esperar a que la canción termine
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);

            clip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}