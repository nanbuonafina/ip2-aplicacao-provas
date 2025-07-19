package proj.provas.aplicacao.controller;

import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.repository.ProvaRepository;
import proj.provas.aplicacao.util.ArquivoUtils;

import java.util.List;

public class ProvaController {
    private final ProvaRepository provaService;

    public ProvaController(ProvaRepository provaService) {
        this.provaService = provaService;
    }

    public void cadastrarProva(Prova prova) {
        //provaService.cadastrarProva(prova);
        ArquivoUtils.salvarProva(prova); // permite persistir o cadastro das provas
    }

    public Prova buscarProvaPorId(String id) {
        return provaService.buscarProvaPorId(id);
    }

    public List<Prova> listarProvas() {
        return provaService.listarProvas();
    }
}
