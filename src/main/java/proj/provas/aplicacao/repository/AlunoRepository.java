package proj.provas.aplicacao.repository;

import proj.provas.aplicacao.model.Aluno;

import java.util.List;

public interface AlunoRepository {
    void cadastrarAluno(Aluno aluno);
    Aluno buscarPorMatricula(String matricula);
    List<Aluno> listarAlunos();
    void removerAluno(String matricula);
    void atualizarEmail(String matricula, String novoEmail);
    void atualizarAluno(Aluno alunoAtualizado);
}
