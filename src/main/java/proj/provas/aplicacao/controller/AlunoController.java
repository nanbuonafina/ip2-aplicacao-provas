package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.repository.AlunoRepository;

import java.util.List;

public class AlunoController {

    private final AlunoRepository alunoService;

    public AlunoController(AlunoRepository alunoService) {
        this.alunoService = alunoService;
    }

    public void cadastrarAluno(Aluno aluno) {
        alunoService.cadastrarAluno(aluno);
    }

    public Aluno buscarAlunoPorMatricula(String matricula) {
        return alunoService.buscarPorMatricula(matricula);
    }

    public List<Aluno> listarAlunos() {
        return alunoService.listarAlunos();
    }

    public void removerAluno(String matricula) {
        alunoService.removerAluno(matricula);
    }

    public void atualizarEmail(String matricula, String novoEmail) {
        alunoService.atualizarEmail(matricula, novoEmail);
    }

    public void atualizarAluno(Aluno aluno) {
        alunoService.atualizarAluno(aluno);
    }
}
