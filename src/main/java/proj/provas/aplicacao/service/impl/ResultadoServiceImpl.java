package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.model.Resposta;
import proj.provas.aplicacao.model.Resultado;
import proj.provas.aplicacao.service.ResultadoService;

public class ResultadoServiceImpl implements ResultadoService {
    @Override
    public Resultado gerarResultado(Aluno aluno, Prova prova, Resposta resposta, String feedback) {
        return new Resultado(aluno, prova, resposta.getNotaTotal(), feedback);
    }
}
