package fidness;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RepositorioUsuarios ru = new RepositorioUsuarios();
            RepositorioEjercicios re = new RepositorioEjercicios();
            RepositorioRutinas rr = new RepositorioRutinas();

            if (re.listar().isEmpty()) {
                cargarEjerciciosIniciales(re);
            }

            AutenticacionService auth = new AutenticacionService(ru);
            new LoginFrame(auth, re, rr).setVisible(true);
        });
    }

    private static void cargarEjerciciosIniciales(RepositorioEjercicios re) {
        try {
            re.agregar(new Ejercicio(1, "Sentadilla", TipoEjercicio.PIERNA,
                    "Ejercicio para fortalecer piernas.",
                    "1. Coloque la barra sobre los hombros.\n2. Baje flexionando rodillas.\n3. Suba controladamente.",
                    "imagenes/sentadilla.gif", 10, true));

            re.agregar(new Ejercicio(2, "Press banca", TipoEjercicio.PECHO,
                    "Ejercicio para pecho.",
                    "1. Acuéstese en el banco.\n2. Empuje la barra hacia arriba.\n3. Baje controladamente.",
                    "imagenes/press_banca.gif", 8, true));

            re.agregar(new Ejercicio(3, "Remo con barra", TipoEjercicio.ESPALDA,
                    "Ejercicio para espalda.",
                    "1. Inclínese con la barra.\n2. Lleve la barra al abdomen.\n3. Baje controladamente.",
                    "imagenes/remo.gif", 12, true));

            re.agregar(new Ejercicio(4, "Curl bíceps", TipoEjercicio.BRAZO,
                    "Ejercicio para bíceps.",
                    "1. Sostenga las mancuernas.\n2. Flexione los codos.\n3. Baje lentamente.",
                    "imagenes/curl.gif", 6, true));

            re.agregar(new Ejercicio(5, "Plancha", TipoEjercicio.ABDOMEN,
                    "Ejercicio para abdomen.",
                    "1. Apóyese en antebrazos y pies.\n2. Mantenga el cuerpo recto.\n3. Sostenga.",
                    "imagenes/plancha.gif", 5, true));

            re.agregar(new Ejercicio(6, "Correr", TipoEjercicio.CARDIO,
                    "Ejercicio cardiovascular.",
                    "1. Mantenga ritmo constante.\n2. Controle la respiración.",
                    "imagenes/correr.gif", 20, true));

            re.guardar();
        } catch (DatosInvalidosException e) {
            System.out.println("Error al cargar ejercicios iniciales: " + e.getMessage());
        }
    }
}