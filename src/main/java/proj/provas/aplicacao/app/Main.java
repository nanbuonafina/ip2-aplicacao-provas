package proj.provas.aplicacao.app;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import proj.provas.aplicacao.model.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Funciona.");

        Turma turma = new Turma("IP2", "2025.1", null, null, null);


        Disciplina disciplina = new Disciplina("Matematica", "Matematica para programaçao", 160);
        List<Disciplina> disciplinasProfessor = new ArrayList<>();
        disciplinasProfessor.add(disciplina);

        Aluno aluno = new Aluno("2025001", "Arthur", "arthur@email.com", turma);

        Professor professor = new Professor("1", "Leandro xx", "leandro@bcc", disciplinasProfessor);

        System.out.println("Aluno " + aluno.getNomeCompleto() + ", estuda na turma " + aluno.getTurma().getIdentificacao());

        List<Questao> questoes = new ArrayList<>();

        List<String> alternativasQ1 = Arrays.asList("2", "3", "4", "5");
        List<String> alternativasQ2 = Arrays.asList("Brasilia", "Salvador", "Recife", "São Paulo");

        QuestaoObjetiva q1 = new QuestaoObjetiva(1, "Quanto é 2 + 2?", alternativasQ1, 2, 2.0);
        QuestaoDissertativa q2 = new QuestaoDissertativa(2, "Explique o Teorema de Pitágoras.", 3.0);
        QuestaoObjetiva q3 = new QuestaoObjetiva(3, "Qual é a capital do Brasil?", alternativasQ2, 0, 2.0);

        questoes.add(q1);
        questoes.add(q2);
        questoes.add(q3);

        Prova prova = new Prova("1", turma, disciplina, professor, LocalDateTime.of(2025, 6, 10, 9, 0), 60, questoes, 7.0);

        System.out.println("===== Prova criada =====");
        System.out.println("Disciplina: " + prova.getDisciplina().getNome());
        System.out.println("Turma: " + prova.getTurma().getIdentificacao());
        System.out.println("Professor: " + prova.getProfessorResponsavel().getNomeCompleto());
        System.out.println("Data da prova: " + prova.getDataAplicacao());
        System.out.println("Duração: " + prova.getDuracaoMinutos() + " minutos");
        System.out.println("Nota total: " + prova.getNotaTotal());
        System.out.println("\nQuestões da prova:");
        for (Questao q : prova.getQuestoes()) {
            System.out.println("- (" + q.getNumero() + ") " + q.getEnunciado() + " | Peso: " + q.getValor());

            if (q instanceof QuestaoObjetiva) {
                QuestaoObjetiva qo = (QuestaoObjetiva) q;
                List<String> alternativas = qo.getAlternativas();
                for (int i = 0; i < alternativas.size(); i++) {
                    System.out.println("   [" + i + "] " + alternativas.get(i));
                }
            }
        }
        System.out.println("=========================");
    }
}
