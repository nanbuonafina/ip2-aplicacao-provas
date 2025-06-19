package proj.provas.aplicacao.service.Impl;

import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.service.ProvaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProvaServiceImpl implements ProvaService {

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
