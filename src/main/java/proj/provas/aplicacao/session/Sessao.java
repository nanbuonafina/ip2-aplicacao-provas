package proj.provas.aplicacao.session;

import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.Professor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sessao {

    private static Sessao instancia;

    private final List<Aluno> alunosCadastrados = new ArrayList<>();
    private final List<Professor> professoresCadastrados = new ArrayList<>();
    private final Map<String, String> credenciais = new HashMap<>();

    private Sessao() {}

    public static Sessao getInstance() {
        if (instancia == null) {
            instancia = new Sessao();
        }
        return instancia;
    }

    public List<Aluno> getAlunosCadastrados() {
        return alunosCadastrados;
    }

    public List<Professor> getProfessoresCadastrados() {
        return professoresCadastrados;
    }

    public Map<String, String> getCredenciais() {
        return credenciais;
    }
}
