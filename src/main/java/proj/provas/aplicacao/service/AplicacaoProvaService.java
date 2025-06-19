package proj.provas.aplicacao.service;

import proj.provas.aplicacao.model.AplicacaoProva;

public interface AplicacaoProvaService {
    void iniciarAplicacao(AplicacaoProva aplicacao);
    void verificarTempo(AplicacaoProva aplicacao);
    void finalizarAplicacao(AplicacaoProva aplicacao);
    void salvarRespostas(AplicacaoProva aplicacao, int numeroQuestao);
}
