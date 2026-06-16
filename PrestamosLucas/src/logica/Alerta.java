package logica;

import java.io.Serializable;
import java.util.Date;

public class Alerta implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipo;        // "unica" o "recurrente"
    private int intervaloDias;
    private String mensaje;
    private Date fechaProxima;

    public Alerta(String tipo, int intervaloDias, String mensaje, Date fechaBase) {
        this.tipo = tipo;
        this.intervaloDias = intervaloDias;
        this.mensaje = mensaje;
        this.fechaProxima = new Date(fechaBase.getTime());
        if (tipo.equals("recurrente") && intervaloDias > 0) {
            this.fechaProxima = new Date(fechaBase.getTime() + (long) intervaloDias * 24 * 60 * 60 * 1000);
        }
    }

    public String getTipo() { return tipo; }
    public int getIntervaloDias() { return intervaloDias; }
    public String getMensaje() { return mensaje; }
    public Date getFechaProxima() { return fechaProxima; }

    public void calcularProximaFecha() {
        if (tipo.equals("recurrente") && intervaloDias > 0) {
            fechaProxima = new Date(fechaProxima.getTime() + (long) intervaloDias * 24 * 60 * 60 * 1000);
        }
    }

    public void mostrar() {
        System.out.println("[ALERTA] " + mensaje + " - Próxima: " + fechaProxima);
    }

    @Override
    public String toString() {
        return mensaje + " (" + tipo + ")";
    }
}