package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.service.QuestaoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestaoServiceImpl implements QuestaoService {

    private final Map<Integer, Questao> questoes = new HashMap<>();

    @Override
    public void adicionarQuestao(Questao questao) {
        if (questoes.containsKey(questao.getNumero())) {
            throw new IllegalArgumentException("Já existe uma questão com esse número.");
        }
        questoes.put(questao.getNumero(), questao);
    }

    @Override
    public Questao buscarPorNumero(int numero) {
        Questao q = questoes.get(numero);
        if (q == null) {
            throw new IllegalArgumentException("Questão não encontrada com o número: " + numero);
        }
        return q;
    }

    @Override
    public List<Questao> listarQuestoes() {
        return new ArrayList<>(questoes.values());
    }

    @Override
    public void removerQuestao(int numero) {
        if (!questoes.containsKey(numero)) {
            throw new IllegalArgumentException("Não existe questão com esse número para remover.");
        }
        questoes.remove(numero);
    }
}
