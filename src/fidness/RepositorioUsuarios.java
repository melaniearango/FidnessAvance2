package fidness;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
    private List<Usuario> lista;
    private final File archivo;

    public RepositorioUsuarios() {
        this.archivo = new File("data/usuarios.dat");
        this.lista = new ArrayList<>();
        cargar();
    }

    @SuppressWarnings("unchecked")
    public void cargar() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                lista = (List<Usuario>) obj;
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar usuarios: " + e.getMessage());
        }
    }

    public void guardar() {
        try {
            archivo.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                oos.writeObject(new ArrayList<>(lista));
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar usuarios: " + e.getMessage());
        }
    }

    public void agregar(Usuario usuario) {
        lista.add(usuario);
    }

    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        for (Usuario u : lista) {
            if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }

    public List<Usuario> listar() {
        return lista;
    }
}