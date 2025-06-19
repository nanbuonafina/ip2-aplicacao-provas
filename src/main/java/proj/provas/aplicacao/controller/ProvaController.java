package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.service.ProvaService;

import java.util.List;

public class ProvaController {
    private final ProvaService provaService;

    public ProvaController(ProvaService provaService) {
        this.provaService = provaService;
    }

    public void cadastrarProva(Prova prova) {
        provaService.cadastrarProva(prova);
    }

    public Prova buscarProvaPorId(String id) {
        return provaService.buscarProvaPorId(id);
    }

    public List<Prova> listarProvas() {
        return provaService.listarProvas();
    }
}
