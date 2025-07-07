package proj.provas.aplicacao.model;

import java.util.List;

public class Professor {
    private String nomeCompleto;
    private String email;
    private List<Disciplina> disciplinasMinistradas;
    private String id;
    private String senha;

    public Professor(String id, String nomeCompleto, String email, List<Disciplina> disciplinasMinistradas, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.disciplinasMinistradas = disciplinasMinistradas;
        this.id = id;
        this.senha = senha;
    }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Disciplina> getDisciplinasMinistradas() { return disciplinasMinistradas; }
    public void setDisciplinasMinistradas(List<Disciplina> disciplinasMinistradas) { this.disciplinasMinistradas = disciplinasMinistradas; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
