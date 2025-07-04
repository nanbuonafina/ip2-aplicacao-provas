package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.repository.QuestaoRepository;

import java.util.List;

public class QuestaoController {

    private final QuestaoRepository questaoService;

    public QuestaoController(QuestaoRepository questaoService) {
        this.questaoService = questaoService;
    }

    public void adicionarQuestao(Questao questao) {
        questaoService.adicionarQuestao(questao);
    }

    public Questao buscarQuestao(int numero) {
        return questaoService.buscarPorNumero(numero);
    }

    public List<Questao> listarQuestoes() {
        return questaoService.listarQuestoes();
    }

    public void removerQuestao(int numero) {
        questaoService.removerQuestao(numero);
    }
}
