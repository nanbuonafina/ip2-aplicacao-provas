package proj.provas.aplicacao.view.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.model.QuestaoDissertativa;
import proj.provas.aplicacao.model.QuestaoObjetiva;

import java.time.LocalDateTime;
import java.util.*;

public class TelaAplicarProvaController {

    @FXML private Label labelDisciplina;
    @FXML private Label labelDataHora;
    @FXML private Label labelDuracao;
    @FXML private Label labelCronometro;
    @FXML private VBox boxQuestoes;
    @FXML private Button btnFinalizar;

    private Prova prova;
    private final Map<Integer, String> respostasAluno = new HashMap<>();
    private Timeline cronometro;

    public void carregarProva(Prova provaSelecionada) {
        this.prova = provaSelecionada;

        labelDisciplina.setText("Disciplina: " + prova.getDisciplina().getNome());
        labelDataHora.setText("Data: " + prova.getDataAplicacao().toString());
        labelDuracao.setText("Duração: " + prova.getDuracaoMinutos() + " min");

        carregarQuestoes();
        iniciarCronometro(prova.getDuracaoMinutos());
    }

    private void carregarQuestoes() {
        boxQuestoes.getChildren().clear();

        for (Questao questao : prova.getQuestoes()) {
            VBox box = new VBox(5);
            box.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-radius: 4;");
            Label lbl = new Label("Q" + questao.getNumero() + ": " + questao.getEnunciado());

            box.getChildren().add(lbl);

            if (questao instanceof QuestaoDissertativa) {
                TextArea resposta = new TextArea();
                resposta.setPromptText("Resposta dissertativa...");
                resposta.setWrapText(true);
                resposta.setPrefRowCount(4);
                resposta.textProperty().addListener((obs, oldVal, newVal) -> {
                    respostasAluno.put(questao.getNumero(), newVal);
                });
                box.getChildren().add(resposta);

            } else if (questao instanceof QuestaoObjetiva qObj) {
                ToggleGroup grupo = new ToggleGroup();
                List<String> alternativas = qObj.getAlternativas();
                for (int i = 0; i < alternativas.size(); i++) {
                    RadioButton alt = new RadioButton((i + 1) + ") " + alternativas.get(i));
                    int finalI = i;
                    alt.setToggleGroup(grupo);
                    alt.setOnAction(e -> respostasAluno.put(questao.getNumero(), String.valueOf(finalI)));
                    box.getChildren().add(alt);
                }
            }

            boxQuestoes.getChildren().add(box);
        }
    }

    private void iniciarCronometro(int minutosTotais) {
        final int[] tempoRestante = {minutosTotais * 60}; // segundos
        atualizarLabelTempo(tempoRestante[0]);

        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tempoRestante[0]--;
            atualizarLabelTempo(tempoRestante[0]);

            if (tempoRestante[0] <= 0) {
                cronometro.stop();
                finalizarProvaAutomaticamente();
            }
        }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }

    private void atualizarLabelTempo(int segundos) {
        int minutos = segundos / 60;
        int seg = segundos % 60;
        labelCronometro.setText(String.format("Tempo restante: %02d:%02d", minutos, seg));
    }

    @FXML
    private void finalizarProvaManualmente() {
        if (confirmarFinalizacao()) {
            encerrarProva();
        }
    }

    private void finalizarProvaAutomaticamente() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Tempo Esgotado");
        alerta.setHeaderText("A prova foi finalizada automaticamente.");
        alerta.setContentText("O tempo da prova chegou ao fim.");
        alerta.showAndWait();
        encerrarProva();
    }

    private void encerrarProva() {
        btnFinalizar.setDisable(true);
        labelCronometro.setText("Prova encerrada.");

        System.out.println("Respostas do aluno:");
        respostasAluno.forEach((numero, resposta) -> {
            System.out.println("Q" + numero + ": " + resposta);
        });

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Prova Finalizada");
        alerta.setHeaderText("Obrigado!");
        alerta.setContentText("Sua prova foi finalizada.");
        alerta.showAndWait();
    }

    private boolean confirmarFinalizacao() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Finalizar Prova");
        alert.setHeaderText("Você deseja finalizar a prova agora?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
