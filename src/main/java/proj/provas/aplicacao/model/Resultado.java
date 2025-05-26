package proj.provas.aplicacao.model;

public class Resultado {

    private Aluno aluno;
    private Prova prova;
    private double notaFinal;
    private String situacao;
    private String resumo;

    public Resultado(Aluno aluno, Prova prova, double notaFinal, String resumo) {
        this.aluno = aluno;
        this.prova = prova;
        this.notaFinal = notaFinal;
        this.resumo = resumo;
        atualizarSituacao();
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Prova getProva() {
        return prova;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public String getSituacao() {
        return situacao;
    }

    public String getResumo() {
        return resumo;
    }

    public void setNotaFinal(double notaFinal) {
        this.notaFinal = notaFinal;
        atualizarSituacao();
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    private void atualizarSituacao() {
        double notaMinima = prova.getNotaTotal() * 0.6;
        this.situacao = (notaFinal >= notaMinima) ? "Aprovado" : "Reprovado";
    }
}
