package proj.provas.aplicacao.model;

import java.util.List;
import java.io.Serializable;

public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String periodo;
    private List<Aluno> alunos;
    private List<Disciplina> disciplinas;
    private List<Professor> professores;

    public Turma(String id, String periodo, List<Aluno> alunos, List<Disciplina> disciplinas, List<Professor> professores) {
        this.id = id;
        this.periodo = periodo;
        this.alunos = alunos;
        this.disciplinas = disciplinas;
        this.professores = professores;
    }

    public String getIdentificacao() { return id; }
    public void setIdentificacao(String id) { this.id = id; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public List<Aluno> getAlunos() { return alunos; }
    public void setAlunos(List<Aluno> alunos) { this.alunos = alunos; }

    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<Disciplina> disciplinas) { this.disciplinas = disciplinas; }

    public List<Professor> getProfessores() { return professores; }
    public void setProfessores(List<Professor> professores) { this.professores = professores; }
}
