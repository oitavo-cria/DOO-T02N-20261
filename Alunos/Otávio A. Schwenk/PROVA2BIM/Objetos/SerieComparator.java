package Objetos;

import java.time.LocalDate;

public class SerieComparator {

    public static int porNome(Serie a, Serie b) {
        return a.getNome().compareToIgnoreCase(b.getNome());
    }

    public static int porNota(Serie a, Serie b) {
        return Double.compare(b.getNotaGeral(), a.getNotaGeral());
    }

    public static int porEstado(Serie a, Serie b) {
        return peso(a.getEstado()) - peso(b.getEstado());
    }

    private static int peso(String estado) {

    if (estado == null) return 3;

        switch (estado.toLowerCase()) {
            case "running":
                return 1;

            case "ended":
                return 2;

            case "canceled":
                return 3;

            default:
                return 4;
        }
    }

    public static int porDataEstreia(Serie a, Serie b) {
        LocalDate da = a.getDataEstreia();
        LocalDate db = b.getDataEstreia();

        if (da == null && db == null) return 0;
        if (da == null) return 1;
        if (db == null) return -1;

        return da.compareTo(db);
    }
}
