package Telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Objetos.Serie;
import Objetos.Usuario;
import Service.SerieService;

public class TelaPrincipal {

    private Usuario usuario;

    JFrame telaPrincipal = new JFrame("HermitNine");

    JPanel fundoPrincipal = new JPanel(new BorderLayout());

    JLabel boasVindas = new JLabel();

    JTextField campoPesquisa = new JTextField();
    JButton buscar = new JButton("Buscar");

    JPanel painelTop = new JPanel(new BorderLayout());
    JPanel resultados = new JPanel(new BorderLayout());
    JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

    JTabbedPane tabs = new JTabbedPane();

    JPanel favoritos = new JPanel(new BorderLayout());
    JPanel assistidas = new JPanel(new BorderLayout());
    JPanel queroAssistir = new JPanel(new BorderLayout());

    JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    private SerieService service = new SerieService();

    private DefaultTableModel modeloSeries;
    private JTable tabelaSeries;

    private DefaultTableModel modeloFavoritos;
    private JTable tabelaFavoritos;

    private DefaultTableModel modeloAssistidas;
    private JTable tabelaAssistidas;

    private DefaultTableModel modeloDesejo;
    private JTable tabelaDesejo;

    private List<Serie> cacheSeries = new ArrayList<>();
    private List<Serie> listaFavoritos = new ArrayList<>();
    private List<Serie> listaAssistidas = new ArrayList<>();
    private List<Serie> listaDesejo = new ArrayList<>();

    public TelaPrincipal(Usuario usuario) {
        this.usuario = usuario;
        criarTelaPrincipal();
        configurarBotoes();
        configurarTabelas();
    }

    public void criarTelaPrincipal() {

        telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);

        boasVindas.setText("Olá, " + usuario.getApelido());
        boasVindas.setFont(new Font("Arial Black", Font.BOLD, 32));

        fundoPrincipal.setLayout(new BorderLayout());
        fundoPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        fundoPrincipal.add(boasVindas, BorderLayout.NORTH);

        campoPesquisa.setFont(new Font("Arial", Font.PLAIN, 16));
        campoPesquisa.setPreferredSize(new Dimension(300, 35));

        buscar.setFont(new Font("Arial Black", Font.BOLD, 14));
        buscar.setBackground(new Color(101, 21, 140));
        buscar.setForeground(Color.WHITE);
        buscar.setFocusPainted(false);

        JPanel buscaContainer = new JPanel(new BorderLayout(10, 10));
        buscaContainer.add(campoPesquisa, BorderLayout.CENTER);
        buscaContainer.add(buscar, BorderLayout.EAST);

        String[] colunas = {
                "Nome", "Idioma", "Gêneros", "Nota",
                "Estado", "Estreia", "Término", "Emissora"
        };

        modeloSeries = new DefaultTableModel(colunas, 0);
        tabelaSeries = new JTable(modeloSeries);

        JScrollPane scrollSeries = new JScrollPane(tabelaSeries);

        JButton btnFav = new JButton("Favoritos");
        JButton btnAss = new JButton("Assistidas");
        JButton btnDesejo = new JButton("Desejo assistir");
        JButton btnRemover = new JButton("Remover");

        painelAcoes.add(btnFav);
        painelAcoes.add(btnAss);
        painelAcoes.add(btnDesejo);
        painelAcoes.add(btnRemover);

        resultados.add(buscaContainer, BorderLayout.NORTH);
        resultados.add(scrollSeries, BorderLayout.CENTER);
        resultados.add(painelAcoes, BorderLayout.SOUTH);

        painelTop.setLayout(new BorderLayout());
        painelTop.add(resultados, BorderLayout.CENTER);

        criarTabelasTabs();

        tabs.setFont(new Font("Arial", Font.BOLD, 14));

        tabs.addTab("Favoritos", favoritos);
        tabs.addTab("Assistidas", assistidas);
        tabs.addTab("Desejo assistir", queroAssistir);

        split.setTopComponent(painelTop);
        split.setBottomComponent(tabs);

        split.setDividerLocation(500);
        split.setResizeWeight(0.65);

        fundoPrincipal.add(split, BorderLayout.CENTER);

        telaPrincipal.add(fundoPrincipal);
        telaPrincipal.setVisible(true);

        btnFav.addActionListener(e -> adicionarSerie(listaFavoritos, modeloFavoritos, "favoritos"));
        btnAss.addActionListener(e -> adicionarSerie(listaAssistidas, modeloAssistidas, "assistidas"));
        btnDesejo.addActionListener(e -> adicionarSerie(listaDesejo, modeloDesejo, "desejo"));

        btnRemover.addActionListener(e -> removerSerieSelecionada());
    }

    private void criarTabelasTabs() {

        String[] colunas = {
                "Nome", "Idioma", "Gêneros", "Nota",
                "Estado", "Estreia", "Término", "Emissora"
        };

        modeloFavoritos = new DefaultTableModel(colunas, 0);
        tabelaFavoritos = new JTable(modeloFavoritos);
        favoritos.add(new JScrollPane(tabelaFavoritos), BorderLayout.CENTER);

        modeloAssistidas = new DefaultTableModel(colunas, 0);
        tabelaAssistidas = new JTable(modeloAssistidas);
        assistidas.add(new JScrollPane(tabelaAssistidas), BorderLayout.CENTER);

        modeloDesejo = new DefaultTableModel(colunas, 0);
        tabelaDesejo = new JTable(modeloDesejo);
        queroAssistir.add(new JScrollPane(tabelaDesejo), BorderLayout.CENTER);
    }

    public void configurarBotoes() {

        buscar.addActionListener(e -> {
            try {

                String query = URLEncoder.encode(
                        campoPesquisa.getText(),
                        StandardCharsets.UTF_8
                );

                cacheSeries = service.buscarSerie(query);

                preencherTabela(cacheSeries);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void preencherTabela(List<Serie> series) {

        modeloSeries.setRowCount(0);

        for (Serie s : series) {
            modeloSeries.addRow(new Object[]{
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

    private void adicionarSerie(List<Serie> lista, DefaultTableModel modelo, String tipo) {

        int row = tabelaSeries.getSelectedRow();
        if (row < 0) return;

        Serie s = cacheSeries.get(row);

        boolean existe = lista.stream()
                .anyMatch(x -> x.getNome().equalsIgnoreCase(s.getNome()));

        if (existe) {
            JOptionPane.showMessageDialog(null, "Já existe em " + tipo, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Adicionar em " + tipo + "?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        lista.add(s);

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

    private void removerSerieSelecionada() {

        int tab = tabs.getSelectedIndex();

        JTable tabelaAtual;
        DefaultTableModel modeloAtual;
        List<Serie> listaAtual;

        if (tab == 0) {
            tabelaAtual = tabelaFavoritos;
            modeloAtual = modeloFavoritos;
            listaAtual = listaFavoritos;
        } else if (tab == 1) {
            tabelaAtual = tabelaAssistidas;
            modeloAtual = modeloAssistidas;
            listaAtual = listaAssistidas;
        } else {
            tabelaAtual = tabelaDesejo;
            modeloAtual = modeloDesejo;
            listaAtual = listaDesejo;
        }

        int row = tabelaAtual.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Selecione uma série para remover");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Remover série?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        listaAtual.remove(row);
        modeloAtual.removeRow(row);
    }

    public void configurarTabelas() {

        tabelaSeries.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tabelaSeries.getSelectedRow();
                if (row >= 0) {
                    System.out.println(modeloSeries.getValueAt(row, 0));
                }
            }
        });
    }
}