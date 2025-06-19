package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.service.ProfessorService;

import java.util.List;

public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    public void cadastrarProfessor(Professor professor) {
        professorService.cadastrarProfessor(professor);
    }

    public Professor buscarProfessor(String id) {
        return professorService.buscarPorId(id);
    }

    public void atualizarEmail(String id, String novoEmail) {
        professorService.atualizarEmail(id, novoEmail);
    }

    public void adicionarDisciplina(String id, Disciplina disciplina) {
        professorService.adicionarDisciplina(id, disciplina);
    }

    public void removerProfessor(String id) {
        professorService.removerProfessor(id);
    }

    public List<Professor> listarProfessores() {
        return professorService.listarProfessores();
    }
}
