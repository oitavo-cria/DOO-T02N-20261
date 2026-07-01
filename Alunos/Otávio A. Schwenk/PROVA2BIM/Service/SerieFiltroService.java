package Service;

import Objetos.Serie;
import Objetos.SerieComparator;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SerieFiltroService {

    public static void aplicarFiltro(
            List<Serie> base,
            DefaultTableModel modelo,
            String textoFiltro,
            String tipoOrdenacao
    ) {

        List<Serie> filtradas = new ArrayList<>(base);

        // FILTRO TEXTO
        if (textoFiltro != null && !textoFiltro.isBlank()){
            String filtro = textoFiltro.toLowerCase();

            filtradas = filtradas.stream()
                    .filter(s -> s.getNome() != null &&
                            s.getNome().toLowerCase().contains(filtro))
                    .collect(Collectors.toList());
        }

        Comparator<Serie> comparator = getComparator(tipoOrdenacao);

        filtradas.sort(comparator);

        modelo.setRowCount(0);

        for (Serie s : filtradas) {
            modelo.addRow(new Object[]{
                    s.getNome(),
                    s.getIdioma(),
                    String.join(", ", s.getGeneros()),
                    s.getNotaGeral(),
                    s.getEstado(),
                    s.getDataEstreia(),
                    s.getDataTermino(),
                    s.getEmissora()
            });
        }
    }

    private static Comparator<Serie> getComparator(String tipoOrdenacao) {

        if (tipoOrdenacao == null) {
            return (a, b) -> SerieComparator.porNome(a, b);
        }

        switch (tipoOrdenacao) {

            case "Nome":
                return (a, b) -> SerieComparator.porNome(a, b);

            case "Nota":
                return (a, b) -> SerieComparator.porNota(a, b);

            case "Estado":
                return (a, b) -> SerieComparator.porEstado(a, b);

            case "Estreia":
                return (a, b) -> SerieComparator.porDataEstreia(a, b);

            default:
                return (a, b) -> SerieComparator.porNome(a, b);
        }
    }
}