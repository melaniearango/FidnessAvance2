package fidness;

import javax.swing.*;
import java.awt.*;

public class DetalleEjercicioFrame extends JFrame {
    public DetalleEjercicioFrame(Ejercicio e) {
        setTitle("Detalle del Ejercicio");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JTextArea area = new JTextArea(e.mostrarDetalle());
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(ev -> dispose());

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(btnVolver, BorderLayout.SOUTH);
    }
}