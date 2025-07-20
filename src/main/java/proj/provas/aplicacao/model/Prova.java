package proj.provas.aplicacao.model;

import java.time.LocalDateTime;
import java.io.Serializable; // isso aq permite que a classe pode ser salva em um arquivo (serialização e desserialização)
import java.util.ArrayList;
import java.util.List;

public class Prova implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Turma turma;
    private Disciplina disciplina;
    private Professor professorResponsavel;
    private LocalDateTime dataAplicacao;
    private int duracaoMinutos;
    private List<Questao> questoes;
    private double notaTotal;
    private List<AplicacaoProva> aplicacoes = new ArrayList<>();


    public Prova(String id, Turma turma, Disciplina disciplina, Professor professorResponsavel,
                 LocalDateTime dataAplicacao, int duracaoMinutos, List<Questao> questoes, double notaTotal) {
        this.id = id;
        this.turma = turma;
        this.disciplina = disciplina;
        this.professorResponsavel = professorResponsavel;
        this.dataAplicacao = dataAplicacao;
        this.duracaoMinutos = duracaoMinutos;
        this.questoes = questoes;
        this.notaTotal = notaTotal;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public Disciplina getDisciplina() { return disciplina; }
    public void setDisciplina(Disciplina disciplina) { this.disciplina = disciplina; }

    public Professor getProfessorResponsavel() { return professorResponsavel; }
    public void setProfessorResponsavel(Professor professorResponsavel) { this.professorResponsavel = professorResponsavel; }

    public LocalDateTime getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDateTime dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public int getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(int duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public List<Questao> getQuestoes() { return questoes; }
    public void setQuestoes(List<Questao> questoes) { this.questoes = questoes; }

    public double getNotaTotal() { return notaTotal; }
    public void setNotaTotal(double notaTotal) { this.notaTotal = notaTotal; }

    public List<AplicacaoProva> getAplicacoes() {
        // so p garantir que n vai retornar null e dar o erro null pointer exception
        if (aplicacoes == null) {
            aplicacoes = new ArrayList<>();
        }
        return aplicacoes;
    }

    public void setAplicacoes(List<AplicacaoProva> aplicacoes) {
        this.aplicacoes = aplicacoes;
    }

    public void adicionarAplicacao(AplicacaoProva aplicacao) {
        this.aplicacoes.add(aplicacao);
    }


    // construtor mais simples ja que a gente ainda nao tem todos os dados (principalmente turma e etc)

    public Prova(String id, Professor professorResponsavel, List<Questao> questoes) {
        this.id = id;
        this.professorResponsavel = professorResponsavel;
        this.questoes = questoes;
        this.dataAplicacao = LocalDateTime.now(); // ou null se quiser deixar em branco
        this.duracaoMinutos = 60; // valor padrão ou modifique depois
        this.notaTotal = calcularNotaTotal(questoes);
    }

    private double calcularNotaTotal(List<Questao> questoes) {
        return questoes.stream().mapToDouble(Questao::getValor).sum();
    }


}
