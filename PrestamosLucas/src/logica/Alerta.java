package logica;

import java.util.Date;

public class Alerta {

    private String mensaje;
    private Date fechaProxima;
    private String tipo;
    private int intervaloDias;

    public Alerta(String tipo, int intervaloDias, String mensaje, Date fechaBase) {
        this.tipo = tipo;
        this.intervaloDias = intervaloDias;
        this.mensaje = mensaje;
        calcularProximaFecha(fechaBase);
    }

    public void calcularProximaFecha(Date fechaBase) {
        long milisegundos = (long) intervaloDias * 24 * 60 * 60 * 1000;
        this.fechaProxima = new Date(fechaBase.getTime() + milisegundos);
    }

    public void mostrar() {
        Date hoy = new Date();
        if (!hoy.before(fechaProxima)) {
            System.out.println("[alerta - " + tipo + "] " + mensaje);
        }
    }

    public String getMensaje() { return mensaje; }
    public String getTipo() { return tipo; }
    public Date getFechaProxima() { return fechaProxima; }
    public int getIntervaloDias() { return intervaloDias; }
    
    @Override    
    public String toString() {
        return tipo + " - " + mensaje + " (proxima: " + fechaProxima + ")";
    }
}