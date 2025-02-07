package tarea1;

public class Cronometro {
    private long inicio;
    private long tiempoTranscurrido;
    private boolean enEjecucion;
    private long tiempoAlmacenado;

    public Cronometro() {
        this.tiempoTranscurrido = 0;
        this.enEjecucion = false;
        this.tiempoAlmacenado = 0;
    }

    public void iniciar() {
        if (!enEjecucion) {
            inicio = System.currentTimeMillis();
            enEjecucion = true;
            System.out.println("Cronómetro iniciado.");
        } else {
            System.out.println("El cronómetro ya está en ejecución.");
        }
    }

    public void detener() {
        if (enEjecucion) {
            tiempoTranscurrido += System.currentTimeMillis() - inicio;
            enEjecucion = false;
            System.out.println("Cronómetro detenido.");
        } else {
            System.out.println("El cronómetro no está en ejecución.");
        }
    }

    public void resetear() {
        almacenarTiempo();  // Almacenar el tiempo transcurrido antes de resetear
        tiempoTranscurrido = 0;
        enEjecucion = false; // Asegurarse de que el cronómetro no esté en ejecución
        System.out.println("Cronómetro reseteado.");
    }

    public long obtenerTiempoTranscurrido() {
        if (enEjecucion) {
            return tiempoTranscurrido + (System.currentTimeMillis() - inicio);
        } else {
            return tiempoTranscurrido;
        }
    }

    public void mostrarTiempoTranscurrido() {
        long tiempoTotal = obtenerTiempoTranscurrido();
        long horas = tiempoTotal / 3600000;
        tiempoTotal %= 3600000;
        long minutos = tiempoTotal / 60000;
        tiempoTotal %= 60000;
        long segundos = tiempoTotal / 1000;
        long milisegundos = tiempoTotal % 1000;

        System.out.println(String.format("Tiempo transcurrido: %02d:%02d:%02d:%03d (hh:mm:ss:ms)", horas, minutos, segundos, milisegundos));
    }

    public void almacenarTiempo() {
        tiempoAlmacenado = obtenerTiempoTranscurrido();
        System.out.println("Tiempo almacenado: " + tiempoAlmacenado + " ms");
    }

    public long obtenerTiempoAlmacenado() {
        return tiempoAlmacenado;
    }
}
