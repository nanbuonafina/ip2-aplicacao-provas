package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Turma;
import proj.provas.aplicacao.service.TurmaService;

import java.util.List;

public class TurmaController {
    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    public void cadastrarTurma(Turma turma) {
        turmaService.cadastrarTurma(turma);
    }

    public Turma buscarPorId(String id) {
        return turmaService.buscarPorId(id);
    }

    public void atualizarTurma(Turma turma) {
        turmaService.atualizarTurma(turma);
    }

    public void removerTurma(String id) {
        turmaService.removerTurma(id);
    }

    public List<Turma> listarTurmas() {
        return turmaService.listarTurmas();
    }
}
