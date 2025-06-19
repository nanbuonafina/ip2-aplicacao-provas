package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.service.DisciplinaService;

import java.util.List;

public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    public void cadastrarDisciplina(Disciplina disciplina) {
        disciplinaService.cadastrarDisciplina(disciplina);
    }

    public Disciplina buscarDisciplina(String nome) {
        return disciplinaService.buscarPorNome(nome);
    }

    public void atualizarDescricao(String nome, String novaDescricao) {
        disciplinaService.atualizarDescricao(nome, novaDescricao);
    }

    public void removerDisciplina(String nome) {
        disciplinaService.removerDisciplina(nome);
    }

    public List<Disciplina> listarDisciplinas() {
        return disciplinaService.listarDisciplinas();
    }
}
