package fidness;

import java.io.Serializable;

public class DetalleRutina implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Ejercicio ejercicio;
    private int series;
    private int repeticiones;
    private int descansoSeg;
    private int orden;

    public DetalleRutina(int id, Ejercicio ejercicio, int series, int repeticiones,
                         int descansoSeg, int orden) {
        this.id = id;
        this.ejercicio = ejercicio;
        this.series = series;
        this.repeticiones = repeticiones;
        this.descansoSeg = descansoSeg;
        this.orden = orden;
    }

    public int calcularVolumen() {
        return series * repeticiones;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Ejercicio getEjercicio() { return ejercicio; }
    public void setEjercicio(Ejercicio ejercicio) { this.ejercicio = ejercicio; }

    public int getSeries() { return series; }
    public void setSeries(int series) { this.series = series; }

    public int getRepeticiones() { return repeticiones; }
    public void setRepeticiones(int repeticiones) { this.repeticiones = repeticiones; }

    public int getDescansoSeg() { return descansoSeg; }
    public void setDescansoSeg(int descansoSeg) { this.descansoSeg = descansoSeg; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }

    @Override
    public String toString() {
        return ejercicio.getNombre() + " - " + series + "x" + repeticiones;
    }
}