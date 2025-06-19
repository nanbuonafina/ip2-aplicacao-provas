package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.service.ProfessorService;

import java.util.*;

public class ProfessorServiceImpl implements ProfessorService {

    private final Map<String, Professor> professores = new HashMap<>();

    @Override
    public void cadastrarProfessor(Professor professor) {
        if (professores.containsKey(professor.getEmail())) {
            throw new IllegalArgumentException("Professor já cadastrado.");
        }
        professores.put(professor.getId(), professor);
    }

    @Override
    public Professor buscarPorId(String id) {
        return professores.get(id);
    }

    @Override
    public void atualizarEmail(String id, String novoEmail) {
        Professor professor = professores.get(id);
        if (professor != null) {
            professor.setEmail(novoEmail);
        } else {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
    }

    @Override
    public void adicionarDisciplina(String id, Disciplina disciplina) {
        Professor professor = professores.get(id);
        if (professor != null) {
            professor.getDisciplinasMinistradas().add(disciplina);
        } else {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
    }

    @Override
    public void removerProfessor(String id) {
        professores.remove(id);
    }

    @Override
    public List<Professor> listarProfessores() {
        return new ArrayList<>(professores.values());
    }
}
