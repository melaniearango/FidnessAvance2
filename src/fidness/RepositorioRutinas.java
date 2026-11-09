package fidness;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioRutinas {
    private List<Rutina> lista;
    private final File archivo;

    public RepositorioRutinas() {
        this.archivo = new File("data/rutinas.dat");
        this.lista = new ArrayList<>();
        cargar();
    }

    @SuppressWarnings("unchecked")
    public void cargar() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                lista = (List<Rutina>) obj;
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar rutinas: " + e.getMessage());
        }
    }

    public void guardar() {
        try {
            archivo.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                oos.writeObject(new ArrayList<>(lista));
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar rutinas: " + e.getMessage());
        }
    }

    public void agregar(Rutina rutina) {
        lista.add(rutina);
    }

    public List<Rutina> listar() {
        return lista;
    }

    public List<Rutina> listarPorUsuario(Usuario usuario) {
        List<Rutina> resultado = new ArrayList<>();
        for (Rutina r : lista) {
            if (r.getUsuario() != null && r.getUsuario().getId() == usuario.getId()) {
                resultado.add(r);
            }
        }
        return resultado;
    }
}