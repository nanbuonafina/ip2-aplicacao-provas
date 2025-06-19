package proj.provas.aplicacao.service;

import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.model.Disciplina;

import java.util.List;

public interface ProfessorService {
    void cadastrarProfessor(Professor professor);
    Professor buscarPorId(String id);
    void atualizarEmail(String id, String novoEmail);
    void adicionarDisciplina(String id, Disciplina disciplina);
    void removerProfessor(String id);
    List<Professor> listarProfessores();
}
