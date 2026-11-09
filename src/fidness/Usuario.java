package fidness;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombreUsuario;
    private String contrasena;
    private String nombreCompleto;
    private RolUsuario rol;
    private boolean activo;

    public Usuario(int id, String nombreUsuario, String contrasena,
                   String nombreCompleto, RolUsuario rol, boolean activo) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.activo = activo;
    }

    public Usuario() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getDescripcionRol() {
        return rol != null ? rol.getDescripcion() : "Sin rol";
    }

    public boolean puedeAdministrar() {
        return rol == RolUsuario.ADMINISTRADOR;
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + nombreUsuario + ")";
    }
}