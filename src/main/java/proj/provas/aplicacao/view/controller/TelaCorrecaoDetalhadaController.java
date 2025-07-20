package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaCorrecaoDetalhadaController {

    @FXML
    private Label labelAluno;

    @FXML
    private VBox vboxQuestoes;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    private AplicacaoProva aplicacaoProva;

    // mapeia cada questão dissertativa ao campo de nota preenchido pelo professor
    private Map<QuestaoDissertativa, TextField> camposNotaDissertativas = new HashMap<>();

    public void setAplicacaoProva(AplicacaoProva aplicacaoProva) {
        this.aplicacaoProva = aplicacaoProva;
        carregarDadosNaTela();
    }

    private void carregarDadosNaTela() {
        labelAluno.setText("Corrigindo prova do aluno: " + aplicacaoProva.getAluno().getNomeCompleto());
        vboxQuestoes.getChildren().clear();

        Prova prova = aplicacaoProva.getProva();
        List<Questao> questoes = prova.getQuestoes();
        List<Resposta> respostas = aplicacaoProva.getRespostas();

        for (int i = 0; i < questoes.size(); i++) {
            Questao questao = questoes.get(i);
            Label labelQuestao = new Label("Questão " + (i + 1) + ": " + questao.getEnunciado());
            labelQuestao.setStyle("-fx-font-weight: bold; -fx-padding: 5 0 0 0;");
            vboxQuestoes.getChildren().add(labelQuestao);

            Resposta resposta = respostas.get(i); //

            if (questao instanceof QuestaoObjetiva) {
                QuestaoObjetiva qo = (QuestaoObjetiva) questao;

                Label labelAlternativas = new Label("Alternativas:");
                vboxQuestoes.getChildren().add(labelAlternativas);

                List<String> alternativas = qo.getAlternativas();
                for (int j = 0; j < alternativas.size(); j++) {
                    String texto = (j == qo.getIdRespostaCorreta()) ? " (Correta)" : "";
                    String alunoEscolheu = (j == resposta.getRespostaObjetiva(i)) ? " <- Resposta do aluno" : "";
                    Label alternativaLabel = new Label(j + ": " + alternativas.get(j) + texto + alunoEscolheu);
                    vboxQuestoes.getChildren().add(alternativaLabel);
                }

            } else if (questao instanceof QuestaoDissertativa) {
                QuestaoDissertativa qd = (QuestaoDissertativa) questao;
                Label respostaAluno = new Label("Resposta do aluno: " + resposta.getRespostasDissertativas().get(i));
                vboxQuestoes.getChildren().add(respostaAluno);

                Label labelNota = new Label("Nota para esta questão:");
                TextField campoNota = new TextField();
                campoNota.setPromptText("Digite a nota da questão");
                vboxQuestoes.getChildren().addAll(labelNota, campoNota);
                camposNotaDissertativas.put(qd, campoNota);
            }

            Separator separator = new Separator();
            vboxQuestoes.getChildren().add(separator);
        }
    }

    @FXML
    private void salvarNotas() {
        double notaFinal = 0.0;

        for (Map.Entry<QuestaoDissertativa, TextField> entry : camposNotaDissertativas.entrySet()) {
            TextField campo = entry.getValue();
            try {
                double nota = Double.parseDouble(campo.getText());
                notaFinal += nota;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Digite notas válidas em todas as questões dissertativas.");
                alert.showAndWait();
                return;
            }
        }

        aplicacaoProva.setDissertativasCorrigidas(true);
        // salvar no objeto resultado

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Correção salva com sucesso! Nota final (dissertativas): " + notaFinal);
        alert.showAndWait();

        btnSalvar.setDisable(true);
    }

    @FXML
    private void voltarParaCorrecao() {
        // logica para voltar para TelaDeCorrecaoDeProvas.fxml
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
