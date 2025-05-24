package proj.provas.aplicacao.model;

public class Aluno {
    private String matricula;
    private String nomeCompleto;
    private String email;
    private Turma turma;

    public Aluno(String matricula, String nomeCompleto, String email, Turma turma) {
        this.matricula = matricula;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.turma = turma;
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Turma getTurma() { return turma; }
    public void setTurma(Turma turma) { this.turma = turma; }
}
