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
        List<AplicacaoProva> aplicacoes = prova.getAplicacoes();

        // substitui ou adiciona a aplicação
        boolean encontrada = false;
        for (int i = 0; i < aplicacoes.size(); i++) {
            if (aplicacoes.get(i).getAluno().equals(aplicacao.getAluno())) {
                aplicacoes.set(i, aplicacao);
                encontrada = true;
                break;
            }
        }
        if (!encontrada) {
            aplicacoes.add(aplicacao);
        }

        List<Prova> provasDoArquivo = ArquivoUtils.carregarProvas();

        for (int i = 0; i < provasDoArquivo.size(); i++) {
            if (provasDoArquivo.get(i).getId().equals(prova.getId())) {
                provasDoArquivo.set(i, prova);
                break;
            }
        }

        ArquivoUtils.salvarTodasProvas(provasDoArquivo);
    }

    @Override
    public void salvarRespostas(AplicacaoProva aplicacao, int numeroQuestao) {
        aplicacao.salvarRespostas(numeroQuestao);
    }
}
