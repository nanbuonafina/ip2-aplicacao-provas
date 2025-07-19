package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.AplicacaoProva;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.service.AplicacaoProvaService;
import proj.provas.aplicacao.util.ArquivoUtils;

import java.util.List;

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

        Prova prova = aplicacao.getProva();

        for (int i = 0; i < prova.getAplicacoes().size(); i++) {
            if (prova.getAplicacoes().get(i).getAluno().equals(aplicacao.getAluno())) {
                prova.getAplicacoes().set(i, aplicacao);
                break;
            }
        }

        // salva todas as provas no arquivo
        List<Prova> provasAtuais = ArquivoUtils.carregarProvas();

        // substitui a prova antiga pela atualizada
        for (int i = 0; i < provasAtuais.size(); i++) {
            if (provasAtuais.get(i).getId().equals(prova.getId())) {
                provasAtuais.set(i, prova);
                break;
            }
        }

        ArquivoUtils.salvarTodasProvas(provasAtuais);
    }

    @Override
    public void salvarRespostas(AplicacaoProva aplicacao, int numeroQuestao) {
        aplicacao.salvarRespostas(numeroQuestao);
    }
}
