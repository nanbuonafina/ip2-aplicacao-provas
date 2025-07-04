package proj.provas.aplicacao.repository;

import proj.provas.aplicacao.model.Prova;

import java.util.List;

public interface ProvaRepository {
    void cadastrarProva(Prova prova);
    Prova buscarProvaPorId(String id);
    List<Prova> listarProvas();
}
