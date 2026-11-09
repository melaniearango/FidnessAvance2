package fidness;

import javax.swing.*;
import java.awt.*;

public class MenuClienteFrame extends JFrame {
    private AutenticacionService auth;
    private RepositorioEjercicios re;
    private RepositorioRutinas rr;
    private Usuario usuario;

    public MenuClienteFrame(AutenticacionService auth, RepositorioEjercicios re,
                            RepositorioRutinas rr, Usuario usuario) {
        this.auth = auth;
        this.re = re;
        this.rr = rr;
        this.usuario = usuario;

        setTitle("FIDNESS - Menú Cliente");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("FIDNESS - Menú Cliente", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblUsuario = new JLabel("Usuario: " + usuario.getNombreCompleto(), SwingConstants.CENTER);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel panelSuperior = new JPanel(new GridLayout(2, 1, 5, 5));
        panelSuperior.add(titulo);
        panelSuperior.add(lblUsuario);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 8, 8));
        JButton btnConsultar = new JButton("Consultar ejercicios");
        JButton btnRutinas = new JButton("Mis rutinas");
        JButton btnCerrar = new JButton("Cerrar sesión");
        
        panelBotones.add(btnConsultar);
        panelBotones.add(btnRutinas);
        panelBotones.add(btnCerrar);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelCentro.add(panelBotones, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);

        btnConsultar.addActionListener(e -> {
            new ConsultarEjerciciosFrame(re, rr, usuario).setVisible(true);
        });

        btnRutinas.addActionListener(e -> {
            new MisRutinasFrame(re, rr, usuario).setVisible(true);
        });

        btnCerrar.addActionListener(e -> {
            auth.logout();
            new LoginFrame(auth, re, rr).setVisible(true);
            dispose();
        });
    }
}