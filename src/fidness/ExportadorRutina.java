package fidness;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ExportadorRutina {

    public boolean exportarTxt(Rutina rutina, File archivo) throws FidnessException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.print(generarContenido(rutina));
            return true;
        } catch (IOException e) {
            throw new FidnessException("Error al exportar la rutina.", e);
        }
    }

    public String generarContenido(Rutina rutina) {
        StringBuilder sb = new StringBuilder();
        sb.append("FIDNESS - RUTINA EXPORTADA\n");
        sb.append("Nombre: ").append(rutina.getNombre()).append("\n");
        sb.append("Usuario: ").append(rutina.getUsuario().getNombreCompleto()).append("\n");
        sb.append("Fecha: ").append(rutina.getFechaCreacion()).append("\n");
        sb.append("Observaciones: ").append(rutina.getObservaciones()).append("\n");
        sb.append("----------------------------------------\n");

        for (DetalleRutina d : rutina.getDetalles()) {
            sb.append(d.getOrden()).append(". ").append(d.getEjercicio().getNombre()).append("\n");
            sb.append("   Tipo: ").append(d.getEjercicio().getTipo().getEtiqueta()).append("\n");
            sb.append("   Series: ").append(d.getSeries()).append("\n");
            sb.append("   Repeticiones: ").append(d.getRepeticiones()).append("\n");
            sb.append("   Descanso: ").append(d.getDescansoSeg()).append(" seg\n");
            sb.append("   Instrucciones: ").append(d.getEjercicio().getInstrucciones()).append("\n\n");
        }

        sb.append("----------------------------------------\n");
        sb.append("Duración total aproximada: ").append(rutina.calcularDuracionTotal()).append(" min.\n");
        return sb.toString();
    }
}