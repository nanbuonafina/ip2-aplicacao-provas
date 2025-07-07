package proj.provas.aplicacao.util;

import proj.provas.aplicacao.controller.ProvaController;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.repository.impl.ProvaRepositoryImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public class DataInitializer {

    private static boolean provasIniciadas = false;

    public static void carregarProvasDeExemplo() {
        if (provasIniciadas) return;

        ProvaController provaController = new ProvaController(ProvaRepositoryImpl.getInstance());

        if (provaController.listarProvas().isEmpty()) {
            Prova prova1 = new Prova(
                    UUID.randomUUID().toString(),
                    new Turma("Turma A", null, null, null, null),
                    new Disciplina("Matemática", null, 60),
                    new Professor("P1", "Prof. João", "joao@escola.com", null, "123"),
                    LocalDateTime.now().plusDays(3),
                    60,
                    Collections.emptyList(),
                    10.0
            );

            Prova prova2 = new Prova(
                    UUID.randomUUID().toString(),
                    new Turma("Turma B", null, null, null, null),
                    new Disciplina("História", null, 60),
                    new Professor("P2", "Prof. Maria", "maria@escola.com", null, "123"),
                    LocalDateTime.now().plusDays(5),
                    45,
                    Collections.emptyList(),
                    8.0
            );

            provaController.cadastrarProva(prova1);
            provaController.cadastrarProva(prova2);
        }

        provasIniciadas = true;
    }
}
