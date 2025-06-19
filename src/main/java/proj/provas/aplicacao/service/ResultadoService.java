package proj.provas.aplicacao.service;

import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.model.Resposta;
import proj.provas.aplicacao.model.Resultado;

public interface ResultadoService {
    Resultado gerarResultado(Aluno aluno, Prova prova, Resposta resposta, String feedback);
}
