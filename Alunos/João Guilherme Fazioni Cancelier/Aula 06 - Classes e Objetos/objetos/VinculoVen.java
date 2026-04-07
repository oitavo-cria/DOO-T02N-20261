package objetos;

public class VinculoVen {
    private int idVen;
    private int idLo;

    // Construtor vazio
    public VinculoVen() {
    }

    // Construtor completo
    public VinculoVen(int idVen, int idLo) {
        this.idVen = idVen;
        this.idLo = idLo;
    }

    // Getters e Setters
    public int getIdVen() {
        return idVen;
    }

    public void setIdVen(int idVen) {
        this.idVen = idVen;
    }

    public int getIdLo() {
        return idLo;
    }

    public void setIdLo(int idLo) {
        this.idLo = idLo;
    }
}
