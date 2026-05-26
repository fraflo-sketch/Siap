package apunab.modelo;

import java.util.Date;

/**
 * Modelo de datos para Apuesta
 * Sistema APUNAB - UNAB
 */
public class Apuesta {
    private int id;
    private String codigoEstudiante;
    private String nombreEstudiante;
    private int lugarId;
    private String lugarNombre;
    private String evento;
    private String descripcion;
    private double monto;
    private String estado; // "PENDIENTE", "GANADA", "PERDIDA", "CANCELADA"
    private Date fechaApuesta;
    private Date fechaEvento;
    private String pronostico;
    private double puntos;

    public Apuesta() {
        this.estado = "PENDIENTE";
        this.fechaApuesta = new Date();
        this.puntos = 0;
    }

    public Apuesta(int id, String codigoEstudiante, String nombreEstudiante,
                   int lugarId, String lugarNombre, String evento,
                   String descripcion, double monto, String estado,
                   Date fechaApuesta, Date fechaEvento, String pronostico, double puntos) {
        this.id = id;
        this.codigoEstudiante = codigoEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.lugarId = lugarId;
        this.lugarNombre = lugarNombre;
        this.evento = evento;
        this.descripcion = descripcion;
        this.monto = monto;
        this.estado = estado;
        this.fechaApuesta = fechaApuesta;
        this.fechaEvento = fechaEvento;
        this.pronostico = pronostico;
        this.puntos = puntos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }

    public String getNombreEstudiante() { return nombreEstudiante; }
    public void setNombreEstudiante(String nombreEstudiante) { this.nombreEstudiante = nombreEstudiante; }

    public int getLugarId() { return lugarId; }
    public void setLugarId(int lugarId) { this.lugarId = lugarId; }

    public String getLugarNombre() { return lugarNombre; }
    public void setLugarNombre(String lugarNombre) { this.lugarNombre = lugarNombre; }

    public String getEvento() { return evento; }
    public void setEvento(String evento) { this.evento = evento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFechaApuesta() { return fechaApuesta; }
    public void setFechaApuesta(Date fechaApuesta) { this.fechaApuesta = fechaApuesta; }

    public Date getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(Date fechaEvento) { this.fechaEvento = fechaEvento; }

    public String getPronostico() { return pronostico; }
    public void setPronostico(String pronostico) { this.pronostico = pronostico; }

    public double getPuntos() { return puntos; }
    public void setPuntos(double puntos) { this.puntos = puntos; }

    @Override
    public String toString() {
        return "Apuesta #" + id + " - " + nombreEstudiante + " [" + estado + "]";
    }
}
