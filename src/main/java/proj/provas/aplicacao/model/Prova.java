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
<<<<<<< HEAD
    private double notaTotal;

    public Prova(String id, Turma turma, Disciplina disciplina, Professor professorResponsavel,
                 LocalDateTime dataAplicacao, int duracaoMinutos, List<Questao> questoes, double notaTotal) {
=======

    public Prova(String id, Turma turma, Disciplina disciplina, Professor professorResponsavel,
                 LocalDateTime dataAplicacao, int duracaoMinutos, List<Questao> questoes) {
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
        this.id = id;
        this.turma = turma;
        this.disciplina = disciplina;
        this.professorResponsavel = professorResponsavel;
        this.dataAplicacao = dataAplicacao;
        this.duracaoMinutos = duracaoMinutos;
        this.questoes = questoes;
<<<<<<< HEAD
        this.notaTotal = notaTotal;
=======
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
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
<<<<<<< HEAD

    public double getNotaTotal() { return notaTotal; }
    public void setNotaTotal(double notaTotal) { this.notaTotal = notaTotal; }
=======
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
}
