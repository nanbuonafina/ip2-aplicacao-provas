package proj.provas.aplicacao.repository.impl;

import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.repository.ProfessorRepository;

import java.util.*;

public class ProfessorRepositoryImpl implements ProfessorRepository {

    private static ProfessorRepositoryImpl instancia;

    private final Map<String, Professor> professores = new HashMap<>();

    // Construtor privado para impedir criação externa
    private ProfessorRepositoryImpl() {}

    // Método para obter a instância única (lazy singleton)
    public static ProfessorRepositoryImpl getInstancia() {
        if (instancia == null) {
            instancia = new ProfessorRepositoryImpl();
        }
        return instancia;
    }

    @Override
    public void cadastrarProfessor(Professor professor) {
        if (professores.containsKey(professor.getId())) {
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
