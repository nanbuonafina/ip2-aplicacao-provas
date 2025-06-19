package proj.provas.aplicacao.service;

import proj.provas.aplicacao.model.Prova;

import java.util.List;

public interface ProvaService {
    void cadastrarProva(Prova prova);
    Prova buscarProvaPorId(String id);
    List<Prova> listarProvas();
}
