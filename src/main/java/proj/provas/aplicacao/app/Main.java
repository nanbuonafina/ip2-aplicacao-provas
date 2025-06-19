package proj.provas.aplicacao.app;

import proj.provas.aplicacao.controller.*;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.service.impl.*;
import proj.provas.aplicacao.service.*;
import proj.provas.aplicacao.service.Impl.ProvaServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Início do teste CRUD ===");

        // --- Criando os serviços e controllers ---
        TurmaServiceImpl turmaService = new TurmaServiceImpl();
        TurmaController turmaController = new TurmaController(turmaService);

        AlunoServiceImpl alunoService = new AlunoServiceImpl();
        AlunoController alunoController = new AlunoController(alunoService);

        ProfessorServiceImpl professorService = new ProfessorServiceImpl();
        ProfessorController professorController = new ProfessorController(professorService);

        DisciplinaServiceImpl disciplinaService = new DisciplinaServiceImpl();
        DisciplinaController disciplinaController = new DisciplinaController(disciplinaService);

        ProvaServiceImpl provaService = new ProvaServiceImpl();
        ProvaController provaController = new ProvaController(provaService);

        QuestaoServiceImpl questaoService = new QuestaoServiceImpl();
        QuestaoController questaoController = new QuestaoController(questaoService);

        // ============================
        // criar uma Turma
        Turma turma = new Turma("T2025-01", "2025.1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        turmaController.cadastrarTurma(turma);
        System.out.println("Turma criada: " + turma.getIdentificacao());

        // criar uma Disciplina
        Disciplina disciplina = new Disciplina("Matemática", "Matemática para Programação", 160);
        disciplinaController.cadastrarDisciplina(disciplina);
        System.out.println("Disciplina criada: " + disciplina.getNome());

        // criar um Professor
        Professor professor = new Professor("P001", "Leandro XX", "leandro@bcc.com", Arrays.asList(disciplina));
        professorController.cadastrarProfessor(professor);
        System.out.println("Professor criado: " + professor.getNomeCompleto());

        // criar um Aluno vinculado à Turma
        Aluno aluno = new Aluno("2025001", "Arthur", "arthur@email.com", turma);
        alunoController.cadastrarAluno(aluno);
        System.out.println("Aluno criado: " + aluno.getNomeCompleto());

        // atualizar a Turma para incluir Aluno, Disciplina e Professor
        turma.getAlunos().add(aluno);
        turma.getDisciplinas().add(disciplina);
        turma.getProfessores().add(professor);
        turmaController.atualizarTurma(turma);
        System.out.println("Turma atualizada com alunos, disciplina e professor.");

        // criar Questões
        QuestaoObjetiva q1 = new QuestaoObjetiva(1, "Quanto é 2 + 2?", Arrays.asList("2", "3", "4", "5"), 2, 2.0);
        QuestaoDissertativa q2 = new QuestaoDissertativa(2, "Explique o Teorema de Pitágoras.", 3.0);
        questaoController.adicionarQuestao(q1);
        questaoController.adicionarQuestao(q2);
        System.out.println("Questões criadas.");

        // criar uma Prova
        List<Questao> questoes = Arrays.asList(q1, q2);
        Prova prova = new Prova("PR001", turma, disciplina, professor, LocalDateTime.of(2025, 6, 10, 9, 0), 60, questoes, 5.0);
        provaController.cadastrarProva(prova);
        System.out.println("Prova criada: " + prova.getId());

        // buscar e listar Alunos da Turma
        System.out.println("\nAlunos da turma " + turma.getIdentificacao() + ":");
        for (Aluno a : turma.getAlunos()) {
            System.out.println("- " + a.getNomeCompleto());
        }

        // atualizar dados do aluno
        aluno.setEmail("arthur_novo@email.com");
        alunoController.atualizarAluno(aluno);
        System.out.println("Aluno atualizado: " + aluno.getNomeCompleto() + " com novo email: " + aluno.getEmail());

        // listar todas as Turmas
        System.out.println("\nListando todas as turmas:");
        for (Turma t : turmaController.listarTurmas()) {
            System.out.println("- " + t.getIdentificacao() + " - Período: " + t.getPeriodo());
        }

        // deletar uma questão
        questaoController.removerQuestao(q1.getNumero());
        System.out.println("Questão " + q1.getNumero() + " removida.");

        // deletar o professor
        professorController.removerProfessor(professor.getId());
        System.out.println("Professor removido.");

        // deletar aluno
        alunoController.removerAluno(aluno.getMatricula());
        System.out.println("Aluno removido.");

        // deletar turma
        turmaController.removerTurma(turma.getIdentificacao());
        System.out.println("Turma removida.");

        // listar turmas para confirmar remoção
        System.out.println("\nTurmas após remoção:");
        List<Turma> turmasRestantes = turmaController.listarTurmas();
        if (turmasRestantes.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
        } else {
            turmasRestantes.forEach(t -> System.out.println("- " + t.getIdentificacao()));
        }

        System.out.println("\n=== Fim do teste CRUD ===");
    }
}
