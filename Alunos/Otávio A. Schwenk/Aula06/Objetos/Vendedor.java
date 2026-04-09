package Objetos;

public class Vendedor {

    private String nome;
    private int idade;
    private String Loja;
    private String cidade;
    private String bairro;
    private String rua;
    private double salarioBase;
    private double salarioRecebido;

    public Vendedor() {
    }

    public Vendedor(String nome, int idade, String Loja, String cidade, String bairro, String rua, double salarioBase, double salarioRecebido) {

    }

    public String getNome() {
	    return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getLoja() {
	    return Loja;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getSalarioRecebido() {
        return salarioRecebido;
    }

    public void setNome(String nome) {
        if(nome != null && !nome.isBlank()) {
            this.nome = nome;
        }
    }

    public void setIdade(int idade) {
        if(idade >= 0) {
            this.idade = idade;
        }
    }

    public void setLoja(String Loja) {
        if(Loja != null && !Loja.isBlank()) {
            this.Loja = Loja;
        }
    }

    public void setCidade(String cidade) {
        if(cidade != null && !cidade.isBlank()) {
            this.cidade = cidade;
        }
    }

    public void setBairro(String bairro) {
        if(bairro != null && !bairro.isBlank()) {
            this.bairro = bairro;
        }
    }

    public void setRua(String rua) {
        if(rua != null && !rua.isBlank()) {
            this.rua = rua;
        }
    }

    public void setSalarioBase(double salarioBase) {
        if(salarioBase >= 0) {
            this.salarioBase = salarioBase;
        }
    }

    public void setSalarioRecebido(double salarioRecebido) {
        if(salarioRecebido >= 0) {
            this.salarioRecebido = salarioRecebido;
        }
    }


    @Override
    public String toString() {
        return "Pessoa [nome=" + nome +
        ", idade=" + idade +
        ", cidade=" + cidade +
        ", bairro=" + bairro +
        ", rua=" + rua +
        ", salarioBase=" + salarioBase +
        ", salarioRecebido=" + salarioRecebido +
        "]";
    }
}
