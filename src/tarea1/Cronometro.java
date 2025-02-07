
package tarea1;

class Cronometro {
    private long inicio;
    private long tiempoTranscurrido;
    private boolean enEjecucion;

    public Cronometro() {
        this.tiempoTranscurrido = 0;
        this.enEjecucion = false;
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
        tiempoTranscurrido = 0;
        if (enEjecucion) {
            inicio = System.currentTimeMillis();
        }
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
        System.out.println("Tiempo transcurrido: " + obtenerTiempoTranscurrido() + " ms");
        
    }
}
