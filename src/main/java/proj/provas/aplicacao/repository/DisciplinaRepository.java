package proj.provas.aplicacao.repository;

import proj.provas.aplicacao.model.Disciplina;

import java.util.List;

public interface DisciplinaRepository {
    void cadastrarDisciplina(Disciplina disciplina);
    Disciplina buscarPorNome(String nome);
    void atualizarDescricao(String nome, String novaDescricao);
    void removerDisciplina(String nome);
    List<Disciplina> listarDisciplinas();
}
