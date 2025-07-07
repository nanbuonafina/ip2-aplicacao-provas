package proj.provas.aplicacao.repository.impl;

import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.repository.ProvaRepository;

import java.util.*;

public class ProvaRepositoryImpl implements ProvaRepository {

    private static ProvaRepositoryImpl instance;

    private final Map<String, Prova> provas = new HashMap<>();

    private ProvaRepositoryImpl() {}

    public static ProvaRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ProvaRepositoryImpl();
        }
        return instance;
    }

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
