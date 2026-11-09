package fidness;

public class AutenticacionService {
    private final RepositorioUsuarios repositorioUsuarios;
    private Usuario usuarioActual;

    public AutenticacionService(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
        crearUsuariosIniciales();
    }

    private void crearUsuariosIniciales() {
        if (repositorioUsuarios.listar().isEmpty()) {
            repositorioUsuarios.agregar(new Administrador(1, "admin", "admin123", "Administrador Fidness"));
            repositorioUsuarios.agregar(new Cliente(2, "juan", "1234", "Juan Pérez"));
            repositorioUsuarios.guardar();
        }
    }

    public Usuario login(String nombreUsuario, String contrasena)
            throws CredencialesInvalidasException {
        Usuario u = repositorioUsuarios.buscarPorNombreUsuario(nombreUsuario);
        if (u == null || !u.getContrasena().equals(contrasena) || !u.isActivo()) {
            throw new CredencialesInvalidasException("Usuario o contraseña incorrectos.");
        }
        usuarioActual = u;
        return u;
    }

    public void logout() {
        usuarioActual = null;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}