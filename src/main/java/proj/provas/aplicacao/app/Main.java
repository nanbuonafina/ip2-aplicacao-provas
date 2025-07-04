package proj.provas.aplicacao.app;

import proj.provas.aplicacao.controller.*;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.repository.impl.*;
import proj.provas.aplicacao.service.impl.*;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Início do teste CRUD e Aplicação de Prova ===");


        // Serviços e Controllers
        TurmaRepositoryImpl turmaService = new TurmaRepositoryImpl();
        TurmaController turmaController = new TurmaController(turmaService);

        AlunoRepositoryImpl alunoService = new AlunoRepositoryImpl();
        AlunoController alunoController = new AlunoController(alunoService);

        ProfessorRepositoryImpl professorService = new ProfessorRepositoryImpl();
        ProfessorController professorController = new ProfessorController(professorService);

        DisciplinaRepositoryImpl disciplinaService = new DisciplinaRepositoryImpl();
        DisciplinaController disciplinaController = new DisciplinaController(disciplinaService);

        ProvaRepositoryImpl provaService = new ProvaRepositoryImpl();
        ProvaController provaController = new ProvaController(provaService);

        QuestaoRepositoryImpl questaoService = new QuestaoRepositoryImpl();
        QuestaoController questaoController = new QuestaoController(questaoService);

        RespostaServiceImpl respostaService = new RespostaServiceImpl();
        ResultadoServiceImpl resultadoService = new ResultadoServiceImpl();

        // Criar turma
        Turma turma = new Turma("T2025-01", "2025.1", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        turmaController.cadastrarTurma(turma);

        // Criar disciplina
        Disciplina disciplina = new Disciplina("Matemática", "Matemática para Programação", 160);
        disciplinaController.cadastrarDisciplina(disciplina);

        // Criar professor
        Professor professor = new Professor("P001", "Leandro XX", "leandro@bcc.com", Arrays.asList(disciplina));
        professorController.cadastrarProfessor(professor);

        // Criar aluno
        Aluno aluno = new Aluno("2025001", "Arthur", "arthur@email.com", turma);
        alunoController.cadastrarAluno(aluno);

        // Vincular aluno, professor e disciplina à turma
        turma.getAlunos().add(aluno);
        turma.getDisciplinas().add(disciplina);
        turma.getProfessores().add(professor);
        turmaController.atualizarTurma(turma);

        // Criar questões
        QuestaoObjetiva q1 = new QuestaoObjetiva(1, "Quanto é 2 + 2?", Arrays.asList("2", "3", "4", "5"), 2, 2.0);
        QuestaoDissertativa q2 = new QuestaoDissertativa(2, "Explique o Teorema de Pitágoras.", 3.0);
        questaoController.adicionarQuestao(q1);
        questaoController.adicionarQuestao(q2);

        List<Questao> questoes = Arrays.asList(q1, q2);

        // Criar prova
        Prova prova = new Prova("PR001", turma, disciplina, professor, LocalDateTime.of(2025, 6, 10, 9, 0), 60, questoes, 5.0);
        provaController.cadastrarProva(prova);

        // Simular Respostas do aluno
        Resposta resposta = new Resposta(aluno, prova);

        // Responder questão objetiva
        resposta.responderObjetivas(q1.getNumero(), "4");

        // Responder questão dissertativa
        resposta.setRespostasDissertativas(q2.getNumero(), "É o teorema que afirma que em um triângulo retângulo, o quadrado da hipotenusa é igual à soma dos quadrados dos catetos.");

        // Corrigir prova
        respostaService.corrigirObjetivas(resposta, questoes);

        Map<Integer, Double> notasDissertativas = new HashMap<>();
        notasDissertativas.put(q2.getNumero(), 2.5); // professor avaliou a dissertativa

        respostaService.corrigirDissertativas(resposta, notasDissertativas);

        // Calcular nota final
        double notaFinal = respostaService.calcularNotaTotal(resposta);

        // Exibir notas
        System.out.println("\nNotas Objetivas:");
        resposta.getNotasObjetivas().forEach((num, nota) -> System.out.println("Questão " + num + ": " + nota));

        System.out.println("Nota Dissertativa:");
        System.out.println("Questão " + q2.getNumero() + ": " + resposta.getNotaDissertativa(q2.getNumero()));

        System.out.println("Nota total: " + notaFinal);

        // Gerar Resultado
        String resumo = "Prova aplicada de forma online via sistema.";
        Resultado resultado = resultadoService.gerarResultado(aluno, prova, resposta, resumo);

        // Exibir resultado final
        System.out.println("\n--- Resultado ---");
        System.out.println("Aluno: " + resultado.getAluno().getNomeCompleto());
        System.out.println("Nota obtida: " + resultado.getNotaFinal());
        System.out.println("Situação: " + resultado.getSituacao());
        System.out.println("Resumo: " + resultado.getResumo());

        System.out.println("\n=== Fim do teste ===");
    }
}
