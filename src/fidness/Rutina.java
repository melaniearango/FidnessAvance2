package fidness;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rutina implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private LocalDate fechaCreacion;
    private Usuario usuario;
    private List<DetalleRutina> detalles;
    private String observaciones;

    public Rutina(int id, String nombre, Usuario usuario, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.observaciones = observaciones;
        this.fechaCreacion = LocalDate.now();
        this.detalles = new ArrayList<>();
    }

    public void agregarEjercicio(Ejercicio ejercicio, int series, int repeticiones,
                                 int descansoSeg) throws DatosInvalidosException {
        if (ejercicio == null) {
            throw new DatosInvalidosException("Debe seleccionar un ejercicio.");
        }
        if (series <= 0 || repeticiones <= 0) {
            throw new DatosInvalidosException("Series y repeticiones deben ser mayores que cero.");
        }
        if (descansoSeg < 0) {
            throw new DatosInvalidosException("El descanso no puede ser negativo.");
        }
        DetalleRutina detalle = new DetalleRutina(detalles.size() + 1, ejercicio,
                series, repeticiones, descansoSeg, detalles.size() + 1);
        detalles.add(detalle);
    }

    public boolean eliminarEjercicio(int idDetalle) {
        return detalles.removeIf(d -> d.getId() == idDetalle);
    }

    public int calcularDuracionTotal() {
        int total = 0;
        for (DetalleRutina d : detalles) {
            total += d.getEjercicio().getDuracionEstimadaMin();
        }
        return total;
    }

    public String obtenerResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rutina: ").append(nombre).append("\n");
        sb.append("Fecha: ").append(fechaCreacion).append("\n");
        sb.append("Usuario: ").append(usuario.getNombreCompleto()).append("\n");
        sb.append("Observaciones: ").append(observaciones).append("\n\n");
        for (DetalleRutina d : detalles) {
            sb.append(d.getOrden()).append(". ").append(d.getEjercicio().getNombre())
              .append(" - ").append(d.getSeries()).append("x").append(d.getRepeticiones())
              .append(" - Descanso: ").append(d.getDescansoSeg()).append(" seg\n");
        }
        sb.append("\nDuración total aproximada: ").append(calcularDuracionTotal()).append(" min.");
        return sb.toString();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<DetalleRutina> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleRutina> detalles) { this.detalles = detalles; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return nombre + " (" + fechaCreacion + ")";
    }
}