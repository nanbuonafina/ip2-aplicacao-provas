package proj.provas.aplicacao.repository.impl;

import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.repository.QuestaoRepository;

import java.util.*;

public class QuestaoRepositoryImpl implements QuestaoRepository {

    private static QuestaoRepositoryImpl instance;

    private final Map<Integer, Questao> questoes = new HashMap<>();

    private QuestaoRepositoryImpl() {}

    public static QuestaoRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new QuestaoRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void adicionarQuestao(Questao questao) {
        questoes.put(questao.getNumero(), questao);
    }

    @Override
    public List<Questao> listarQuestoes() {
        return new ArrayList<>(questoes.values());
    }

    @Override
    public Questao buscarPorNumero(int numero) {
        return questoes.get(numero);
    }

    @Override
    public void removerQuestao(int numero) {
        questoes.remove(numero);
    }
}
