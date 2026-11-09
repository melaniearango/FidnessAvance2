package fidness;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MisRutinasFrame extends JFrame {
    private RepositorioEjercicios re;
    private RepositorioRutinas rr;
    private Usuario usuario;
    private DefaultTableModel modelo;
    private JTable tabla;
    private List<Rutina> listaRutinas;

    public MisRutinasFrame(RepositorioEjercicios re, RepositorioRutinas rr, Usuario usuario) {
        this.re = re;
        this.rr = rr;
        this.usuario = usuario;

        setTitle("FIDNESS - Mis Rutinas");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Fecha", "N° Ejercicios", "Duración"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);

        JPanel botones = new JPanel();
        JButton btnCrear = new JButton("Crear");
        JButton btnVer = new JButton("Ver");
        JButton btnExportar = new JButton("Exportar");
        JButton btnVolver = new JButton("Volver");
        
        botones.add(btnCrear);
        botones.add(btnVer);
        botones.add(btnExportar);
        botones.add(btnVolver);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        btnCrear.addActionListener(e -> {
            new CrearRutinaFrame(re, rr, usuario).setVisible(true);
            refrescarTabla();
        });

        btnVer.addActionListener(e -> verRutina());
        btnExportar.addActionListener(e -> exportarRutina());
        btnVolver.addActionListener(e -> dispose());

        refrescarTabla();
    }

    private void refrescarTabla() {
        modelo.setRowCount(0);
        listaRutinas = rr.listarPorUsuario(usuario);
        for (Rutina r : listaRutinas) {
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getNombre(),
                    r.getFechaCreacion(),
                    r.getDetalles().size(),
                    r.calcularDuracionTotal() + " min"
            });
        }
    }

    private void verRutina() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una rutina.");
            return;
        }
        Rutina r = listaRutinas.get(fila);
        JTextArea area = new JTextArea(r.obtenerResumen());
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));
        JOptionPane.showMessageDialog(this, scroll, "Detalle de rutina", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportarRutina() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una rutina.");
            return;
        }
        Rutina r = listaRutinas.get(fila);
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar rutina como...");
        chooser.setSelectedFile(new File(r.getNombre().replaceAll("\\s+", "_") + ".txt"));
        
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".txt")) {
                f = new File(f.getAbsolutePath() + ".txt");
            }
            try {
                new ExportadorRutina().exportarTxt(r, f);
                JOptionPane.showMessageDialog(this, "Rutina exportada en:\n" + f.getAbsolutePath());
            } catch (FidnessException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}