package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.model.Resposta;
import proj.provas.aplicacao.service.RespostaService;

import java.util.List;
import java.util.Map;

public class RespostaController {
    private final RespostaService respostaService;

    public RespostaController(RespostaService respostaService) {
        this.respostaService = respostaService;
    }

    public void corrigir(Resposta resposta, List<Questao> questoes, Map<Integer, Double> notasDissertativas) {
        respostaService.corrigirObjetivas(resposta, questoes);
        respostaService.corrigirDissertativas(resposta, notasDissertativas);
        respostaService.calcularNotaTotal(resposta);
    }

    public void corrigirObjetivas(Resposta resposta, List<Questao> questoes) {
        respostaService.corrigirObjetivas(resposta, questoes);
    }

    public void corrigirDissertativas(Resposta resposta, Map<Integer, Double> notasDissertativas) {
        respostaService.corrigirDissertativas(resposta, notasDissertativas);
    }

    public void calcularNotaTotal(Resposta resposta) {
        respostaService.calcularNotaTotal(resposta);
    }
}
