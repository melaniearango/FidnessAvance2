package fidness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultarEjerciciosFrame extends JFrame {
    private final RepositorioEjercicios re;
    private final RepositorioRutinas rr;
    private final Usuario usuario;
    private final JComboBox<String> cbTipo = new JComboBox<>();
    private final JTextField txtBuscar = new JTextField();
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private List<Ejercicio> listaActual = new ArrayList<>();

    public ConsultarEjerciciosFrame(RepositorioEjercicios re, RepositorioRutinas rr, Usuario usuario) {
        this.re = re;
        this.rr = rr;
        this.usuario = usuario;

        setTitle("Consultar Ejercicios");
        setSize(750, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        cbTipo.addItem("Todos");
        for (TipoEjercicio t : TipoEjercicio.values()) {
            cbTipo.addItem(t.getEtiqueta());
        }

        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtros.add(new JLabel("Tipo:"));
        filtros.add(cbTipo);
        filtros.add(new JLabel("Buscar:"));
        txtBuscar.setColumns(15);
        filtros.add(txtBuscar);
        JButton btnBuscar = new JButton("Buscar");
        filtros.add(btnBuscar);

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Tipo", "Duración"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);

        JPanel botones = new JPanel();
        JButton btnDetalle = new JButton("Ver detalle");
        JButton btnVolver = new JButton("Volver");
        botones.add(btnDetalle);
        botones.add(btnVolver);

        add(filtros, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> refrescar());
        cbTipo.addActionListener(e -> refrescar());
        btnDetalle.addActionListener(e -> verDetalle());
        btnVolver.addActionListener(e -> dispose());

        refrescar();
    }

    private void refrescar() {
        modelo.setRowCount(0);
        listaActual.clear();
        String tipoStr = (String) cbTipo.getSelectedItem();
        TipoEjercicio tipo = "Todos".equals(tipoStr) ? null : TipoEjercicio.desdeEtiqueta(tipoStr);
        String texto = txtBuscar.getText().trim().toLowerCase();

        for (Ejercicio e : re.listar()) {
            if (tipo != null && e.getTipo() != tipo) continue;
            if (!texto.isEmpty() && !e.getNombre().toLowerCase().contains(texto)) continue;
            listaActual.add(e);
            modelo.addRow(new Object[]{e.getId(), e.getNombre(),
                    e.getTipo().getEtiqueta(), e.getDuracionEstimadaMin()});
        }

        if (listaActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron ejercicios.");
        }
    }

    private void verDetalle() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ejercicio.");
            return;
        }
        Ejercicio e = listaActual.get(fila);
        new DetalleEjercicioFrame(e).setVisible(true);
    }
}