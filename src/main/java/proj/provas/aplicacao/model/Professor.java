package proj.provas.aplicacao.model;

import java.util.List;

public class Professor {
    private String nomeCompleto;
    private String email;
    private List<Disciplina> disciplinasMinistradas;
<<<<<<< HEAD
    private String id;
=======
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd

    public Professor(String id, String nomeCompleto, String email, List<Disciplina> disciplinasMinistradas) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.disciplinasMinistradas = disciplinasMinistradas;
<<<<<<< HEAD
        this.id = id;
=======
>>>>>>> cf3d8dd43cd9262f60e0ed4ec4d005aec6efcccd
    }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Disciplina> getDisciplinasMinistradas() { return disciplinasMinistradas; }
    public void setDisciplinasMinistradas(List<Disciplina> disciplinasMinistradas) { this.disciplinasMinistradas = disciplinasMinistradas; }
}
