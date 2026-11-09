package fidness;

public enum TipoEjercicio {
    PIERNA, ESPALDA, BRAZO, PECHO, CARDIO, ABDOMEN;

    public String getEtiqueta() {
        switch (this) {
            case PIERNA: return "Pierna";
            case ESPALDA: return "Espalda";
            case BRAZO: return "Brazo";
            case PECHO: return "Pecho";
            case CARDIO: return "Cardio";
            case ABDOMEN: return "Abdomen";
            default: return name();
        }
    }

    public static TipoEjercicio desdeEtiqueta(String etiqueta) {
        for (TipoEjercicio t : values()) {
            if (t.getEtiqueta().equalsIgnoreCase(etiqueta)) {
                return t;
            }
        }
        return null;
    }
}