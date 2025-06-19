package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.service.DisciplinaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisciplinaServiceImpl implements DisciplinaService {

    private final Map<String, Disciplina> disciplinas = new HashMap<>();

    @Override
    public void cadastrarDisciplina(Disciplina disciplina) {
        if (disciplinas.containsKey(disciplina.getNome())) {
            throw new IllegalArgumentException("Disciplina já cadastrada.");
        }
        disciplinas.put(disciplina.getNome(), disciplina);
    }

    @Override
    public Disciplina buscarPorNome(String nome) {
        return disciplinas.get(nome);
    }

    @Override
    public void atualizarDescricao(String nome, String novaDescricao) {
        Disciplina disciplina = disciplinas.get(nome);
        if (disciplina != null) {
            disciplina.setDescricao(novaDescricao);
        } else {
            throw new IllegalArgumentException("Disciplina não encontrada.");
        }
    }

    @Override
    public void removerDisciplina(String nome) {
        disciplinas.remove(nome);
    }

    @Override
    public List<Disciplina> listarDisciplinas() {
        return new ArrayList<>(disciplinas.values());
    }
}
