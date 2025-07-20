package proj.provas.aplicacao.model;

import java.io.Serializable;
import java.util.Objects;

public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;

    private String matricula;
    private String nomeCompleto;
    private String email;
    private Turma turma;
    private String senha;

    public Aluno(String matricula, String nomeCompleto, String email, Turma turma, String senha) {
        this.matricula = matricula;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.turma = turma;
        this.senha = senha;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Aluno aluno = (Aluno) obj;
        return this.getMatricula().equals(aluno.getMatricula()); // ou outro identificador único
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatricula()); // mesmo campo usado no equals
    }
}
