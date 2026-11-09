package fidness;

public class Cliente extends Usuario {
    private static final long serialVersionUID = 1L;

    public Cliente(int id, String nombreUsuario, String contrasena, String nombreCompleto) {
        super(id, nombreUsuario, contrasena, nombreCompleto, RolUsuario.CLIENTE, true);
    }

    @Override
    public String getDescripcionRol() {
        return "Cliente";
    }

    @Override
    public boolean puedeAdministrar() {
        return false;
    }
}