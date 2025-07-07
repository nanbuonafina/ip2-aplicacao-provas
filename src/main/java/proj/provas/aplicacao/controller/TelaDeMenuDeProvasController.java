package proj.provas.aplicacao.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import proj.provas.aplicacao.model.Disciplina;
import proj.provas.aplicacao.model.Professor;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.model.Turma;
import proj.provas.aplicacao.repository.impl.ProvaRepositoryImpl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public class TelaDeMenuDeProvasController {

    @FXML
    private TextField campoId;

    @FXML
    private TextField campoDisciplina;

    @FXML
    private TextField campoTurma;

    @FXML
    private TextField campoProfessor;

    @FXML
    private DatePicker campoData;

    @FXML
    private TextField campoHora;

    @FXML
    private TextField campoDuracao;

    @FXML
    private TextField campoNotaTotal;

    @FXML
    private Label labelMensagem;

    private final ProvaController provaController = new ProvaController(ProvaRepositoryImpl.getInstance());

    @FXML
    private void cadastrarProva() {
        try {
            String id = campoId.getText().isBlank() ? UUID.randomUUID().toString() : campoId.getText();
            String nomeDisciplina = campoDisciplina.getText();
            String nomeTurma = campoTurma.getText();
            String nomeProfessor = campoProfessor.getText();
            String horaTexto = campoHora.getText();

            if (nomeDisciplina.isBlank() || nomeTurma.isBlank() || nomeProfessor.isBlank()
                    || campoData.getValue() == null || horaTexto.isBlank()
                    || campoDuracao.getText().isBlank() || campoNotaTotal.getText().isBlank()) {
                exibirMensagem("Todos os campos devem ser preenchidos!", false);
                return;
            }

            // Validação da hora
            String[] partesHora = horaTexto.split(":");
            if (partesHora.length != 2) throw new IllegalArgumentException("Hora inválida. Use o formato HH:mm.");

            int hora = Integer.parseInt(partesHora[0]);
            int minuto = Integer.parseInt(partesHora[1]);
            LocalDateTime dataHora = campoData.getValue().atTime(hora, minuto);

            int duracao = Integer.parseInt(campoDuracao.getText());
            double notaTotal = Double.parseDouble(campoNotaTotal.getText());

            Prova prova = new Prova(
                    id,
                    new Turma(nomeTurma, null, null, null, null),
                    new Disciplina(nomeDisciplina, null, 0),
                    new Professor(null, nomeProfessor, "", null, ""),
                    dataHora,
                    duracao,
                    Collections.emptyList(), // Nenhuma questão ainda
                    notaTotal
            );

            provaController.cadastrarProva(prova);
            exibirMensagem("Prova cadastrada com sucesso!", true);
            limparCampos();

        } catch (NumberFormatException e) {
            exibirMensagem("Duração e nota devem ser valores numéricos válidos.", false);
        } catch (Exception e) {
            exibirMensagem("Erro: " + e.getMessage(), false);
        }
    }

    private void exibirMensagem(String mensagem, boolean sucesso) {
        labelMensagem.setText(mensagem);
        labelMensagem.setStyle(sucesso ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    private void limparCampos() {
        campoId.clear();
        campoDisciplina.clear();
        campoTurma.clear();
        campoProfessor.clear();
        campoData.setValue(null);
        campoHora.clear();
        campoDuracao.clear();
        campoNotaTotal.clear();
    }
}
