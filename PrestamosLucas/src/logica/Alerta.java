package logica;

import java.util.Date;

/**
 * Alerta asociada a un préstamo que hace recordatorio de cuando se tiene que devolver el articulo prestao
 */
public class Alerta {

    private String tipo;
    private int intervaloDias;
    private String mensaje;
    private Date fechaProxima;

    // Constructor que recibe la fecha base para calcular cuándo mostrar la alerta
    public Alerta(String tipo, int intervaloDias, String mensaje, Date fechaBase) {
        this.tipo = tipo;
        this.intervaloDias = intervaloDias;
        this.mensaje = mensaje;
        calcularProximaFecha(fechaBase);
    }

    // Calcula la próxima fecha sumando los días de intervalo a la fecha base
    public void calcularProximaFecha(Date fechaBase) {
        long milisegundos = (long) intervaloDias * 24 * 60 * 60 * 1000;
        this.fechaProxima = new Date(fechaBase.getTime() + milisegundos);
    }

    // Muestra la alerta en consola si ya llegó la fecha
    public void mostrar() {
        Date hoy = new Date();
        if (!hoy.before(fechaProxima)) {
            System.out.println("[ALERTA - " + tipo + "] " + mensaje);
        }
    }

    // Getters y Setters
    public String getTipo() { return tipo; }
    public int getIntervaloDias() { return intervaloDias; }
    public String getMensaje() { return mensaje; }
    public Date getFechaProxima() { return fechaProxima; }

    @Override
    public String toString() {
        return tipo + " - " + mensaje + " (próxima: " + fechaProxima + ")";
    }
}