package proj.provas.aplicacao.model;

public class QuestaoDissertativa extends Questao {
    private double valor;

    public QuestaoDissertativa(int numero, String enunciado, double valor) {
        super(numero, enunciado, valor);
        this.valor = valor;
    }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}
