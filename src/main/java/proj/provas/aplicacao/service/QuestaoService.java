package proj.provas.aplicacao.service;

import proj.provas.aplicacao.model.Questao;

import java.util.List;

public interface QuestaoService {
    void adicionarQuestao(Questao questao);
    Questao buscarPorNumero(int numero);
    List<Questao> listarQuestoes();
}
