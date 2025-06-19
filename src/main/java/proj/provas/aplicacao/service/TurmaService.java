package proj.provas.aplicacao.service;

import proj.provas.aplicacao.model.Turma;
import java.util.*;

public interface TurmaService {
    void cadastrarTurma(Turma turma);
    Turma buscarPorId(String id);
    void atualizarTurma(Turma turma);
    void removerTurma(String id);
    List<Turma> listarTurmas();
}
