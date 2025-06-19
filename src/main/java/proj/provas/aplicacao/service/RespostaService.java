package proj.provas.aplicacao.service;

import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.model.Resposta;

import java.util.List;
import java.util.Map;

public interface RespostaService {
    void corrigirObjetivas(Resposta resposta, List<Questao> questoes);
    void corrigirDissertativas(Resposta resposta, Map<Integer, Double> notas);
    double calcularNotaTotal(Resposta resposta);
}
