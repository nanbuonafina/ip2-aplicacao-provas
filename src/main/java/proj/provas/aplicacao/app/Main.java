package proj.provas.aplicacao.app;

import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.Turma;

public class Main {
    public static void main(String[] args) {
        System.out.println("Funciona.");

        Turma turma = new Turma("IP2", "2025.1", null, null, null);

        Aluno aluno = new Aluno("2025001", "Arthur", "arthur@email.com", turma);

        System.out.println("Aluno " + aluno.getNomeCompleto() + ", estuda na turma " + aluno.getTurma().getIdentificacao());
    }
}
