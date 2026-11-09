package fidness;

public class Administrador extends Usuario {
    private static final long serialVersionUID = 1L;

    public Administrador(int id, String nombreUsuario, String contrasena, String nombreCompleto) {
        super(id, nombreUsuario, contrasena, nombreCompleto, RolUsuario.ADMINISTRADOR, true);
    }

    @Override
    public String getDescripcionRol() {
        return "Administrador (acceso total)";
    }

    @Override
    public boolean puedeAdministrar() {
        return true;
    }
}