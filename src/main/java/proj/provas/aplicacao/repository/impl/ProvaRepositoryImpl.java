package proj.provas.aplicacao.repository.impl;

import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.repository.ProvaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvaRepositoryImpl implements ProvaRepository {

    private final Map<String, Prova> provas = new HashMap<>();

    @Override
    public void cadastrarProva(Prova prova) {
        if (provas.containsKey(prova.getId())) {
            throw new IllegalArgumentException("Já existe uma prova com esse ID.");
        }
        provas.put(prova.getId(), prova);
    }

    @Override
    public Prova buscarProvaPorId(String id) {
        Prova prova = provas.get(id);
        if (prova == null) {
            throw new IllegalArgumentException("Prova não encontrada com o ID: " + id);
        }
        return prova;
    }

    @Override
    public List<Prova> listarProvas() {
        return new ArrayList<>(provas.values());
    }
}
