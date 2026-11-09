package fidness;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AutenticacionService auth;
    private final RepositorioEjercicios re;
    private final RepositorioRutinas rr;

    private final JTextField txtUsuario = new JTextField();
    private final JPasswordField txtContrasena = new JPasswordField();

    public LoginFrame(AutenticacionService auth, RepositorioEjercicios re, RepositorioRutinas rr) {
        this.auth = auth;
        this.re = re;
        this.rr = rr;

        setTitle("FIDNESS - Acceso al Sistema");
        setSize(360, 240);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 8, 8));

        JLabel titulo = new JLabel("FIDNESS - Iniciar Sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel p1 = new JPanel(new GridLayout(1, 2));
        p1.add(new JLabel("Usuario:"));
        p1.add(txtUsuario);

        JPanel p2 = new JPanel(new GridLayout(1, 2));
        p2.add(new JLabel("Contraseña:"));
        p2.add(txtContrasena);

        JPanel p3 = new JPanel();
        JButton btnIngresar = new JButton("Ingresar");
        JButton btnSalir = new JButton("Salir");
        p3.add(btnIngresar);
        p3.add(btnSalir);

        add(titulo);
        add(p1);
        add(p2);
        add(p3);

        btnIngresar.addActionListener(e -> ingresar());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void ingresar() {
        try {
            Usuario u = auth.login(txtUsuario.getText().trim(),
                    new String(txtContrasena.getPassword()));
            if (u.puedeAdministrar()) {
                new MenuAdminFrame(auth, re, rr).setVisible(true);
            } else {
                new MenuClienteFrame(auth, re, rr, u).setVisible(true);
            }
            dispose();
        } catch (CredencialesInvalidasException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}