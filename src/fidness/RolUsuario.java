package fidness;

public enum RolUsuario {
    ADMINISTRADOR, CLIENTE;

    public String getDescripcion() {
        return this == ADMINISTRADOR ? "Administrador" : "Cliente";
    }
}