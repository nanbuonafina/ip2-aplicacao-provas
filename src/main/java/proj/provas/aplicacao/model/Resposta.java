package proj.provas.aplicacao.model;

import java.util.HashMap;
import java.util.Map;

public class Resposta {
    private Aluno aluno;
    private Prova prova;

    private Map<Integer, String> respostasObjetivas;
    private Map<Integer, String> respostasDissertativas;
    private Map<Integer, Double> notasDissertativas;

    public Resposta(Aluno aluno, Prova prova) {
        this.aluno = aluno;
        this.prova = prova;
        this.respostasDissertativas = new HashMap<>();
        this.respostasObjetivas = new HashMap<>();
        this.notasDissertativas = new HashMap<>();
    }

    public Aluno getaluno() {
        return aluno;
    }
    public Prova getprova() {
        return prova; }

    public void responderObjetivas(int numeroQuestao, String alternativas) {
        respostasObjetivas.put(numeroQuestao, alternativas); }

    public void setRespostasDissertativas(int numeroQuestao, String resposta) {
        respostasDissertativas.put(numeroQuestao, resposta); }

    public String getRespostaObjetiva(int numeroQuestao) {
        return respostasObjetivas.get(numeroQuestao); }

    public String getRespostaDissertativa(int numeroQuestao) {
        return respostasDissertativas.get(numeroQuestao); }

    public void atribuirNotasDissertativas(int numeroQuestao, double nota) {
        notasDissertativas.put(numeroQuestao, nota); }

    public Double getNotaDissertativa(int numeroQuestao) {
        return notasDissertativas.get(numeroQuestao); }

    public Map<Integer, String> getRespostasDissertativas() {
        return respostasDissertativas; }

    public Map<Integer, String> getRespostasObjetivas() {
        return respostasObjetivas; }

}
