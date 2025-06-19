package proj.provas.aplicacao.service.impl;

import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.service.AlunoService;

import java.util.*;

public class AlunoServiceImpl implements AlunoService {

    private final Map<String, Aluno> alunos = new HashMap<>();

    @Override
    public void cadastrarAluno(Aluno aluno) {
        if (alunos.containsKey(aluno.getMatricula())) {
            throw new IllegalArgumentException("Já existe um aluno com essa matrícula.");
        }
        alunos.put(aluno.getMatricula(), aluno);
    }

    @Override
    public Aluno buscarPorMatricula(String matricula) {
        Aluno aluno = alunos.get(matricula);
        if (aluno == null) {
            throw new NoSuchElementException("Aluno não encontrado para a matrícula: " + matricula);
        }
        return aluno;
    }

    @Override
    public List<Aluno> listarAlunos() {
        return new ArrayList<>(alunos.values());
    }

    @Override
    public void removerAluno(String matricula) {
        if (!alunos.containsKey(matricula)) {
            throw new NoSuchElementException("Aluno não encontrado para remoção.");
        }
        alunos.remove(matricula);
    }

    @Override
    public void atualizarEmail(String matricula, String novoEmail) {
        Aluno aluno = buscarPorMatricula(matricula);
        aluno.setEmail(novoEmail);
    }

    @Override
    public void atualizarAluno(Aluno alunoAtualizado) {
        String matricula = alunoAtualizado.getMatricula();
        if (!alunos.containsKey(matricula)) {
            throw new IllegalArgumentException("Aluno não encontrado com a matrícula: " + matricula);
        }

        // Substitui os dados do aluno existente
        Aluno alunoExistente = alunos.get(matricula);
        alunoExistente.setNomeCompleto(alunoAtualizado.getNomeCompleto());
        alunoExistente.setEmail(alunoAtualizado.getEmail());
        alunoExistente.setTurma(alunoAtualizado.getTurma());

        // Opcional: colocar de volta no map (não necessário, pois objeto é referência)
        alunos.put(matricula, alunoExistente);
    }
}
