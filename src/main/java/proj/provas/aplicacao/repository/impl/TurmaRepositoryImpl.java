package proj.provas.aplicacao.repository.impl;

import proj.provas.aplicacao.model.Turma;
import proj.provas.aplicacao.repository.TurmaRepository;

import java.util.*;

public class TurmaRepositoryImpl implements TurmaRepository {
    private Map<String, Turma> turmas = new HashMap<>();

    @Override
    public void cadastrarTurma(Turma turma) {
        if (turmas.containsKey(turma.getIdentificacao())) {
            throw new IllegalArgumentException("Turma já cadastrada.");
        }
        turmas.put(turma.getIdentificacao(), turma);
    }

    @Override
    public Turma buscarPorId(String id) {
        return turmas.get(id);
    }

    @Override
    public void atualizarTurma(Turma turma) {
        if (!turmas.containsKey(turma.getIdentificacao())) {
            throw new IllegalArgumentException("Turma não encontrada.");
        }
        turmas.put(turma.getIdentificacao(), turma);
    }

    @Override
    public void removerTurma(String id) {
        turmas.remove(id);
    }

    @Override
    public List<Turma> listarTurmas() {
        return new ArrayList<>(turmas.values());
    }
}
