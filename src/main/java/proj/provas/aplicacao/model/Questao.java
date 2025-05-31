package proj.provas.aplicacao.model;

public abstract class Questao {
    private int numero;
    private String enunciado;
<<<<<<< HEAD
    private double valor;

    public Questao(int numero, String enunciado, double valor) {
        this.numero = numero;
        this.enunciado = enunciado;
        this.valor = valor;
=======

    public Questao(int numero, String enunciado) {
        this.numero = numero;
        this.enunciado = enunciado;
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }
<<<<<<< HEAD

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
=======
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
}
