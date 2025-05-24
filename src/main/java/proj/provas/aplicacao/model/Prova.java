package proj.provas.aplicacao.model;

import java.time.LocalDateTime;
import java.util.List;

public class Prova {
    private String id;
    private Turma turma;
    private Disciplina disciplina;
    private Professor professorResponsavel;
    private LocalDateTime dataAplicacao;
    private int duracaoMinutos;
    private List<Questao> questoes;

    public Prova(String id, Turma turma, Disciplina disciplina, Professor professorResponsavel,
                 LocalDateTime dataAplicacao, int duracaoMinutos, List<Questao> questoes) {
        this.id = id;
        this.turma = turma;
        this.disciplina = disciplina;
        this.professorResponsavel = professorResponsavel;
        this.dataAplicacao = dataAplicacao;
        this.duracaoMinutos = duracaoMinutos;
        this.questoes = questoes;
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
}
