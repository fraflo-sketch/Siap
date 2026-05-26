package apunab.modelo;

/**
 * Modelo de datos para Lugar
 * Sistema APUNAB - UNAB
 */
public class Lugar {
    private int id;
    private String nombre;
    private String descripcion;
    private String ciudad;
    private String tipo; // "Cancha", "Estadio", "Virtual", "Sala"
    private boolean activo;

    public Lugar() {}

    public Lugar(int id, String nombre, String descripcion, String ciudad, String tipo, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.tipo = tipo;
        this.activo = activo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + " (" + ciudad + ")";
    }
}
