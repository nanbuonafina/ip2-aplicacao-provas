package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.model.QuestaoObjetiva;
import proj.provas.aplicacao.model.Resposta;
import proj.provas.aplicacao.service.RespostaService;

import java.util.List;
import java.util.Map;

public class RespostaServiceImpl implements RespostaService {
    @Override
    public void corrigirObjetivas(Resposta resposta, List<Questao> questoes) {
        for (Questao q : questoes) {
            if (q instanceof QuestaoObjetiva qo) {
                Integer indiceRespostaAluno = resposta.getRespostaObjetiva(qo.getNumero());

                double nota = (indiceRespostaAluno != null && indiceRespostaAluno == qo.getIdRespostaCorreta())
                        ? qo.getValor()
                        : 0.0;

                resposta.getNotasObjetivas().put(qo.getNumero(), nota);
            }
        }
    }


    @Override
    public void corrigirDissertativas(Resposta resposta, Map<Integer, Double> notas) {
        for (Map.Entry<Integer, Double> entry : notas.entrySet()) {
            resposta.atribuirNotasDissertativas(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public double calcularNotaTotal(Resposta resposta) {
        return resposta.calcularNotaTotal();
    }
}
