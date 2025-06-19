package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.AplicacaoProva;
import proj.provas.aplicacao.service.AplicacaoProvaService;

public class AplicacaoProvaServiceImpl implements AplicacaoProvaService {

    @Override
    public void iniciarAplicacao(AplicacaoProva aplicacao) {
        // o construtor já inicia o autosave, então aqui pode ser algo como log, registro, etc.
        System.out.println("Aplicação iniciada para o aluno: " + aplicacao.getAluno().getNomeCompleto());
    }

    @Override
    public void verificarTempo(AplicacaoProva aplicacao) {
        aplicacao.isTempoEsgotado();
    }

    @Override
    public void finalizarAplicacao(AplicacaoProva aplicacao) {
        aplicacao.finalizarAplicacao();
    }

    @Override
    public void salvarRespostas(AplicacaoProva aplicacao, int numeroQuestao) {
        aplicacao.salvarRespostas(numeroQuestao);
    }
}
