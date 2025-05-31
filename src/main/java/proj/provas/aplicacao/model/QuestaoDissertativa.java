package proj.provas.aplicacao.model;

public class QuestaoDissertativa extends Questao {
    private double valor;

    public QuestaoDissertativa(int numero, String enunciado, double valor) {
<<<<<<< HEAD
        super(numero, enunciado, valor);
=======
        super(numero, enunciado);
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
        this.valor = valor;
    }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}
