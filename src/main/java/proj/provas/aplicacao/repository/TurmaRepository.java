package proj.provas.aplicacao.repository;

import proj.provas.aplicacao.model.Turma;
import java.util.*;

public interface TurmaRepository {
    void cadastrarTurma(Turma turma);
    Turma buscarPorId(String id);
    void atualizarTurma(Turma turma);
    void removerTurma(String id);
    List<Turma> listarTurmas();
}
