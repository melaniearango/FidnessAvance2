package fidness;

import javax.swing.*;
import java.awt.*;

public class MenuAdminFrame extends JFrame {
    private final AutenticacionService auth;
    private final RepositorioEjercicios re;
    private final RepositorioRutinas rr;

    public MenuAdminFrame(AutenticacionService auth, RepositorioEjercicios re, RepositorioRutinas rr) {
        this.auth = auth;
        this.re = re;
        this.rr = rr;

        setTitle("FIDNESS - Menú Administrador");
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 8, 8));

        JLabel titulo = new JLabel("FIDNESS - Menú Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnEjercicios = new JButton("Administrar ejercicios");
        JButton btnCerrar = new JButton("Cerrar sesión");

        add(titulo);
        add(btnEjercicios);
        add(btnCerrar);

        btnEjercicios.addActionListener(e ->
                new AdministrarEjerciciosFrame(re).setVisible(true));

        btnCerrar.addActionListener(e -> {
            auth.logout();
            new LoginFrame(auth, re, rr).setVisible(true);
            dispose();
        });
    }
}