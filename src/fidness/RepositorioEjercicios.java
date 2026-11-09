package fidness;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEjercicios {
    private List<Ejercicio> lista;
    private final File archivo;

    public RepositorioEjercicios() {
        this.archivo = new File("data/ejercicios.dat");
        this.lista = new ArrayList<>();
        cargar();
    }

    @SuppressWarnings("unchecked")
    public void cargar() {
        if (!archivo.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                lista = (List<Ejercicio>) obj;
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar ejercicios: " + e.getMessage());
        }
    }

    public void guardar() {
        try {
            archivo.getParentFile().mkdirs();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
                oos.writeObject(new ArrayList<>(lista));
            }
        } catch (IOException e) {
            System.out.println("No se pudo guardar ejercicios: " + e.getMessage());
        }
    }

    public void agregar(Ejercicio ejercicio) {
        lista.add(ejercicio);
    }

    public void actualizar(Ejercicio ejercicio) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == ejercicio.getId()) {
                lista.set(i, ejercicio);
                return;
            }
        }
    }

    public boolean eliminar(int id) {
        return lista.removeIf(e -> e.getId() == id);
    }

    public List<Ejercicio> listar() {
        return lista;
    }

    public List<Ejercicio> buscarPorTipo(TipoEjercicio tipo) {
        List<Ejercicio> resultado = new ArrayList<>();
        for (Ejercicio e : lista) {
            if (e.getTipo() == tipo) {
                resultado.add(e);
            }
        }
        return resultado;
    }
}