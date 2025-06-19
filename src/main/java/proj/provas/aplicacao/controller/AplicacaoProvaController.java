package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.AplicacaoProva;
import proj.provas.aplicacao.service.AplicacaoProvaService;

public class AplicacaoProvaController {

    private final AplicacaoProvaService aplicacaoProvaService;

    public AplicacaoProvaController(AplicacaoProvaService aplicacaoProvaService) {
        this.aplicacaoProvaService = aplicacaoProvaService;
    }

    public void iniciarAplicacao(AplicacaoProva aplicacao) {
        aplicacaoProvaService.iniciarAplicacao(aplicacao);
    }

    public void verificarTempo(AplicacaoProva aplicacao) {
        aplicacaoProvaService.verificarTempo(aplicacao);
    }

    public void finalizarAplicacao(AplicacaoProva aplicacao) {
        aplicacaoProvaService.finalizarAplicacao(aplicacao);
    }

    public void salvarRespostas(AplicacaoProva aplicacao, int numeroQuestao) {
        aplicacaoProvaService.salvarRespostas(aplicacao, numeroQuestao);
    }
}
