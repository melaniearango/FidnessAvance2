package fidness;

import java.io.Serializable;

public class Ejercicio implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private TipoEjercicio tipo;
    private String descripcion;
    private String instrucciones;
    private String rutaRecurso;
    private int duracionEstimadaMin;
    private boolean activo;

    public Ejercicio(int id, String nombre, TipoEjercicio tipo, String descripcion,
                     String instrucciones, String rutaRecurso, int duracionEstimadaMin,
                     boolean activo) throws DatosInvalidosException {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.instrucciones = instrucciones;
        this.rutaRecurso = rutaRecurso;
        this.duracionEstimadaMin = duracionEstimadaMin;
        this.activo = activo;
        validar();
    }

    public void validar() throws DatosInvalidosException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre del ejercicio es obligatorio.");
        }
        if (tipo == null) {
            throw new DatosInvalidosException("Debe seleccionar un tipo de ejercicio.");
        }
        if (duracionEstimadaMin <= 0) {
            throw new DatosInvalidosException("La duración debe ser mayor que cero.");
        }
    }

    public String mostrarDetalle() {
        return "Nombre: " + nombre + "\n"
                + "Tipo: " + (tipo != null ? tipo.getEtiqueta() : "") + "\n"
                + "Descripción: " + descripcion + "\n"
                + "Instrucciones: " + instrucciones + "\n"
                + "Recurso: " + (rutaRecurso == null || rutaRecurso.isEmpty() ? "Sin recurso visual" : rutaRecurso) + "\n"
                + "Duración estimada: " + duracionEstimadaMin + " min";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoEjercicio getTipo() { return tipo; }
    public void setTipo(TipoEjercicio tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getInstrucciones() { return instrucciones; }
    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }

    public String getRutaRecurso() { return rutaRecurso; }
    public void setRutaRecurso(String rutaRecurso) { this.rutaRecurso = rutaRecurso; }

    public int getDuracionEstimadaMin() { return duracionEstimadaMin; }
    public void setDuracionEstimadaMin(int duracionEstimadaMin) { this.duracionEstimadaMin = duracionEstimadaMin; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre;
    }
}