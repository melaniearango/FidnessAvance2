package fidness;

import javax.swing.*;
import java.awt.*;

public class CrearRutinaFrame extends JFrame {
    private final RepositorioEjercicios re;
    private final RepositorioRutinas rr;
    private final Usuario usuario;

    private final JTextField txtNombre = new JTextField();
    private final DefaultListModel<Ejercicio> modDisponibles = new DefaultListModel<>();
    private final DefaultListModel<Ejercicio> modSeleccionados = new DefaultListModel<>();
    private final JList<Ejercicio> lstDisponibles = new JList<>(modDisponibles);
    private final JList<Ejercicio> lstSeleccionados = new JList<>(modSeleccionados);
    private final JSpinner spSeries = new JSpinner(new SpinnerNumberModel(4, 1, 20, 1));
    private final JSpinner spReps = new JSpinner(new SpinnerNumberModel(12, 1, 100, 1));
    private final JSpinner spDescanso = new JSpinner(new SpinnerNumberModel(60, 0, 300, 5));
    private final JTextArea txtObs = new JTextArea(3, 20);

    public CrearRutinaFrame(RepositorioEjercicios re, RepositorioRutinas rr, Usuario usuario) {
        this.re = re;
        this.rr = rr;
        this.usuario = usuario;

        setTitle("Crear Rutina");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel superior = new JPanel(new GridLayout(1, 2));
        superior.add(new JLabel("Nombre de la rutina:"));
        superior.add(txtNombre);

        JPanel listas = new JPanel(new GridLayout(1, 3, 8, 8));
        JPanel pDisp = new JPanel(new BorderLayout());
        pDisp.add(new JLabel("Ejercicios disponibles"), BorderLayout.NORTH);
        pDisp.add(new JScrollPane(lstDisponibles), BorderLayout.CENTER);

        JPanel pBotones = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton btnAgregar = new JButton("Agregar ->");
        JButton btnQuitar = new JButton("<- Quitar");
        pBotones.add(btnAgregar);
        pBotones.add(btnQuitar);

        JPanel pSel = new JPanel(new BorderLayout());
        pSel.add(new JLabel("Ejercicios seleccionados"), BorderLayout.NORTH);
        pSel.add(new JScrollPane(lstSeleccionados), BorderLayout.CENTER);

        listas.add(pDisp);
        listas.add(pBotones);
        listas.add(pSel);

        JPanel inferior = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel campos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campos.add(new JLabel("Series:"));
        campos.add(spSeries);
        campos.add(new JLabel("Repeticiones:"));
        campos.add(spReps);
        campos.add(new JLabel("Descanso (seg):"));
        campos.add(spDescanso);

        JPanel obsPanel = new JPanel(new BorderLayout());
        obsPanel.add(new JLabel("Observaciones:"), BorderLayout.NORTH);
        obsPanel.add(new JScrollPane(txtObs), BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        botones.add(btnGuardar);
        botones.add(btnCancelar);

        inferior.add(campos);
        inferior.add(obsPanel);

        add(superior, BorderLayout.NORTH);
        add(listas, BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);
        add(botones, BorderLayout.EAST);

        for (Ejercicio e : re.listar()) {
            modDisponibles.addElement(e);
        }

        btnAgregar.addActionListener(e -> agregar());
        btnQuitar.addActionListener(e -> quitar());
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());
    }

    CrearRutinaFrame(RepositorioRutinas rr, RepositorioRutinas rr0, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void agregar() {
        int idx = lstDisponibles.getSelectedIndex();
        if (idx == -1) return;
        Ejercicio e = modDisponibles.remove(idx);
        modSeleccionados.addElement(e);
    }

    private void quitar() {
        int idx = lstSeleccionados.getSelectedIndex();
        if (idx == -1) return;
        Ejercicio e = modSeleccionados.remove(idx);
        modDisponibles.addElement(e);
    }

    private void guardar() {
        try {
            if (txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe escribir un nombre para la rutina.");
                return;
            }
            if (modSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe agregar al menos un ejercicio.");
                return;
            }

            int id = rr.listar().stream().mapToInt(Rutina::getId).max().orElse(0) + 1;
            Rutina rutina = new Rutina(id, txtNombre.getText().trim(), usuario,
                    txtObs.getText().trim());

            for (int i = 0; i < modSeleccionados.size(); i++) {
                Ejercicio e = modSeleccionados.get(i);
                rutina.agregarEjercicio(e,
                        (Integer) spSeries.getValue(),
                        (Integer) spReps.getValue(),
                        (Integer) spDescanso.getValue());
            }

            rr.agregar(rutina);
            rr.guardar();
            JOptionPane.showMessageDialog(this, "Rutina guardada correctamente.");
            dispose();
        } catch (DatosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}