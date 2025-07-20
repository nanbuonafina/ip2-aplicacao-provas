package proj.provas.aplicacao.model;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class Resposta implements Serializable {

    private static final long serialVersionUID = 1L;

    private Aluno aluno;
    private Prova prova;
    private double nota;

    private Map<Integer, Integer> respostasObjetivas;
    private Map<Integer, Double> notasObjetivas;
    private Map<Integer, String> respostasDissertativas;
    private Map<Integer, Double> notasDissertativas;

    public Resposta(Aluno aluno, Prova prova) {
        this.aluno = aluno;
        this.prova = prova;
        this.nota = 0.0;
        this.respostasDissertativas = new HashMap<>();
        this.notasObjetivas = new HashMap<>();
        this.respostasObjetivas = new HashMap<>();
        this.notasDissertativas = new HashMap<>();
    }

    public Aluno getaluno() {
        return aluno;
    }
    public Prova getprova() { return prova; }

    public void responderObjetivas(int numeroQuestao, int indiceAlternativa) {
        respostasObjetivas.put(numeroQuestao, indiceAlternativa);
    }

    public void setRespostasDissertativas(int numeroQuestao, String resposta) {
        respostasDissertativas.put(numeroQuestao, resposta); }

    public Integer getRespostaObjetiva(int numeroQuestao) {
        return respostasObjetivas.get(numeroQuestao);
    }

    public String getRespostaDissertativa(int numeroQuestao) {
        return respostasDissertativas.get(numeroQuestao); }

    public void atribuirNotasDissertativas(int numeroQuestao, double nota) {
        notasDissertativas.put(numeroQuestao, nota); }

    public double getNotaDissertativa(int numeroQuestao) {
        return notasDissertativas.get(numeroQuestao); }

    public Map<Integer, String> getRespostasDissertativas() {
        return respostasDissertativas; }

    public Map<Integer, Double> getNotasObjetivas() {
        return notasObjetivas;
    }

    public double getNotaTotal() {return nota;}

    public double calcularNotaTotal() {
        double soma = 0.0;
        for (double nota : notasObjetivas.values()) {
            soma += nota;
        }
        for (double nota : notasDissertativas.values()) {
            soma += nota;
        }
        this.nota = soma;
        return soma;
    }


}
