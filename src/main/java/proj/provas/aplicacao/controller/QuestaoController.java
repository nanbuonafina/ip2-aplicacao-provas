package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.service.QuestaoService;

import java.util.List;

public class QuestaoController {

    private final QuestaoService questaoService;

    public QuestaoController(QuestaoService questaoService) {
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
}
