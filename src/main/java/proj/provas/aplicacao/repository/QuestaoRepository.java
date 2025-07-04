package proj.provas.aplicacao.repository;

import proj.provas.aplicacao.model.Questao;

import java.util.List;

public interface QuestaoRepository {
    void adicionarQuestao(Questao questao);
    Questao buscarPorNumero(int numero);
    List<Questao> listarQuestoes();
    void removerQuestao(int numero);

}
