package fidness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdministrarEjerciciosFrame extends JFrame {
    private final RepositorioEjercicios re;
    private final JTextField txtNombre = new JTextField();
    private final JComboBox<TipoEjercicio> cbTipo = new JComboBox<>(TipoEjercicio.values());
    private final JTextField txtDescripcion = new JTextField();
    private final JTextField txtInstrucciones = new JTextField();
    private final JTextField txtRecurso = new JTextField();
    private final JTextField txtDuracion = new JTextField();
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private int idEditando = -1;

    public AdministrarEjerciciosFrame(RepositorioEjercicios re) {
        this.re = re;

        setTitle("Administrar Ejercicios");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel formulario = new JPanel(new GridLayout(6, 2, 5, 5));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Tipo:"));
        formulario.add(cbTipo);
        formulario.add(new JLabel("Descripción:"));
        formulario.add(txtDescripcion);
        formulario.add(new JLabel("Instrucciones:"));
        formulario.add(txtInstrucciones);
        formulario.add(new JLabel("Recurso:"));
        formulario.add(txtRecurso);
        formulario.add(new JLabel("Duración (min):"));
        formulario.add(txtDuracion);

        JPanel botones = new JPanel();
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");
        botones.add(btnNuevo);
        botones.add(btnGuardar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnVolver);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Tipo", "Duración"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnVolver.addActionListener(e -> dispose());

        refrescar();
    }

    private void refrescar() {
        modelo.setRowCount(0);
        for (Ejercicio e : re.listar()) {
            modelo.addRow(new Object[]{e.getId(), e.getNombre(),
                    e.getTipo().getEtiqueta(), e.getDuracionEstimadaMin()});
        }
    }

    private void limpiar() {
        idEditando = -1;
        txtNombre.setText("");
        cbTipo.setSelectedIndex(0);
        txtDescripcion.setText("");
        txtInstrucciones.setText("");
        txtRecurso.setText("");
        txtDuracion.setText("");
        tabla.clearSelection();
    }

    private void guardar() {
        try {
            int duracion = Integer.parseInt(txtDuracion.getText().trim());
            int id = idEditando == -1
                    ? re.listar().stream().mapToInt(Ejercicio::getId).max().orElse(0) + 1
                    : idEditando;

            Ejercicio e = new Ejercicio(id, txtNombre.getText().trim(),
                    (TipoEjercicio) cbTipo.getSelectedItem(),
                    txtDescripcion.getText().trim(),
                    txtInstrucciones.getText().trim(),
                    txtRecurso.getText().trim(), duracion, true);

            if (idEditando == -1) {
                re.agregar(e);
            } else {
                re.actualizar(e);
            }
            re.guardar();
            refrescar();
            limpiar();
            JOptionPane.showMessageDialog(this, "Ejercicio guardado.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La duración debe ser un número.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DatosInvalidosException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ejercicio.");
            return;
        }
        int id = (int) modelo.getValueAt(fila, 0);
        for (Ejercicio e : re.listar()) {
            if (e.getId() == id) {
                idEditando = id;
                txtNombre.setText(e.getNombre());
                cbTipo.setSelectedItem(e.getTipo());
                txtDescripcion.setText(e.getDescripcion());
                txtInstrucciones.setText(e.getInstrucciones());
                txtRecurso.setText(e.getRutaRecurso());
                txtDuracion.setText(String.valueOf(e.getDuracionEstimadaMin()));
                return;
            }
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ejercicio.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el ejercicio seleccionado?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) modelo.getValueAt(fila, 0);
            re.eliminar(id);
            re.guardar();
            refrescar();
            limpiar();
        }
    }
}