package proj.provas.aplicacao.app;

import proj.provas.aplicacao.controller.RespostaController;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.service.RespostaService;
import proj.provas.aplicacao.service.ResultadoService;
import proj.provas.aplicacao.service.impl.RespostaServiceImpl;
import proj.provas.aplicacao.service.impl.ResultadoServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Funciona.");

        // Simula criação de turma, disciplina, aluno, professor
        Turma turma = new Turma("IP2", "2025.1", null, null, null);
        Disciplina disciplina = new Disciplina("Matematica", "Matematica para programação", 160);
        List<Disciplina> disciplinasProfessor = new ArrayList<>();
        disciplinasProfessor.add(disciplina);

        Aluno aluno = new Aluno("2025001", "Arthur", "arthur@email.com", turma);
        Professor professor = new Professor("1", "Leandro xx", "leandro@bcc", disciplinasProfessor);

        System.out.println("Aluno " + aluno.getNomeCompleto() + ", estuda na turma " + aluno.getTurma().getIdentificacao());

        // Cria as questões da prova
        List<Questao> questoes = new ArrayList<>();
        questoes.add(new QuestaoObjetiva(1, "Quanto é 2 + 2?", Arrays.asList("2", "3", "4", "5"), 2, 2.0));
        questoes.add(new QuestaoDissertativa(2, "Explique o Teorema de Pitágoras.", 3.0));
        questoes.add(new QuestaoObjetiva(3, "Qual é a capital do Brasil?", Arrays.asList("Brasilia", "Salvador", "Recife", "São Paulo"), 0, 2.0));

        Prova prova = new Prova("1", turma, disciplina, professor, LocalDateTime.of(2025, 6, 10, 9, 0), 60, questoes, 7.0);

        // Exibe informações da prova
        System.out.println("===== Prova criada =====");
        System.out.println("Disciplina: " + prova.getDisciplina().getNome());
        System.out.println("Turma: " + prova.getTurma().getIdentificacao());
        System.out.println("Professor: " + prova.getProfessorResponsavel().getNomeCompleto());
        System.out.println("Data da prova: " + prova.getDataAplicacao());
        System.out.println("Duração: " + prova.getDuracaoMinutos() + " minutos");
        System.out.println("Nota total: " + prova.getNotaTotal());

        for (Questao q : prova.getQuestoes()) {
            System.out.println("- (" + q.getNumero() + ") " + q.getEnunciado() + " | Peso: " + q.getValor());
            if (q instanceof QuestaoObjetiva qo) {
                for (int i = 0; i < qo.getAlternativas().size(); i++) {
                    System.out.println("   [" + i + "] " + qo.getAlternativas().get(i));
                }
            }
        }

        // Cria resposta do aluno
        Resposta respostaAluno1 = new Resposta(aluno, prova);
        respostaAluno1.responderObjetivas(1, "4");
        respostaAluno1.responderObjetivas(3, "Brasilia");
        respostaAluno1.setRespostasDissertativas(2, "É um teorema que relaciona os lados de um triângulo retângulo.");

        // Usa o service/controller para corrigir e calcular nota
        RespostaService respostaService = new RespostaServiceImpl();
        RespostaController respostaController = new RespostaController(respostaService);

        respostaController.corrigirObjetivas(respostaAluno1, prova.getQuestoes());

        Map<Integer, Double> notasDissertativas = new HashMap<>();
        notasDissertativas.put(2, 2.5);
        respostaController.corrigirDissertativas(respostaAluno1, notasDissertativas);

        respostaController.calcularNotaTotal(respostaAluno1);

        // Gera resultado
        ResultadoService resultadoService = new ResultadoServiceImpl();
        Resultado resultadoAluno1 = resultadoService.gerarResultado(
                aluno,
                prova,
                respostaAluno1,
                "Acertou as objetivas e foi bem na dissertativa."
        );

        // Exibe resultado
        System.out.println("\n===== RESULTADOS FINAIS =====");
        System.out.println("Aluno: " + resultadoAluno1.getAluno().getNomeCompleto());
        System.out.println("Nota final: " + resultadoAluno1.getNotaFinal() + " / " + resultadoAluno1.getProva().getNotaTotal());
        System.out.println("Situação: " + resultadoAluno1.getSituacao());
        System.out.println("Resumo: " + resultadoAluno1.getResumo());

        // Relatórios
        List<Resultado> resultados = Arrays.asList(resultadoAluno1);
        Relatorio.gerarRelatorioPorAluno(aluno, resultados);
        Relatorio.gerarRelatorioPorTurma(turma, resultados);
        Relatorio.gerarRelatorioPorDisciplina(disciplina, resultados);
        Relatorio.exportarParaCSV(resultados, "relatorio.csv");
        Relatorio.exportarParaPDF(resultados, "relatorio.pdf");
    }
}