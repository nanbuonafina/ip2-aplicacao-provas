package proj.provas.aplicacao.view.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.service.AplicacaoProvaService;
import proj.provas.aplicacao.service.impl.AplicacaoProvaServiceImpl;
import proj.provas.aplicacao.session.Sessao;

import java.time.LocalDateTime;
import java.util.*;

public class TelaAplicarProvaController {

    @FXML private Label labelDisciplina;
    @FXML private Label labelData;
    @FXML private Label labelDuracao;
    @FXML private Label labelCronometro;
    @FXML private VBox boxQuestoes;
    @FXML private Button btnFinalizar;

    private Prova prova;
    private final Map<Integer, String> respostasAluno = new HashMap<>();
    private Timeline cronometro;

    private final AplicacaoProvaService aplicacaoProvaService = new AplicacaoProvaServiceImpl();
    private AplicacaoProva aplicacaoProva;
    private ProvaFinalizadaListener callback;


    public void setProvaFinalizadaListener(ProvaFinalizadaListener callback) {
        this.callback = callback;
    }

    public void carregarProva(Prova provaSelecionada) {
        this.prova = provaSelecionada;

        Aluno alunoLogado = (Aluno) Sessao.getInstance().getUsuarioLogado();
        String idAplicacao = UUID.randomUUID().toString();

        this.aplicacaoProva = new AplicacaoProva(idAplicacao, prova, alunoLogado, LocalDateTime.now());
        aplicacaoProvaService.iniciarAplicacao(aplicacaoProva);

        labelDisciplina.setText("Disciplina: " + prova.getDisciplina().getNome());
        labelData.setText("Data: " + prova.getDataAplicacao().toString());
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
                    Resposta respostaAluno = obterOuCriarResposta(aplicacaoProva.getAluno());
                    respostaAluno.setRespostasDissertativas(questao.getNumero(), newVal);
                    aplicacaoProvaService.salvarRespostas(aplicacaoProva, questao.getNumero());
                });
                box.getChildren().add(resposta);

            } else if (questao instanceof QuestaoObjetiva qObj) {
                ToggleGroup grupo = new ToggleGroup();
                List<String> alternativas = qObj.getAlternativas();
                for (int i = 0; i < alternativas.size(); i++) {
                    RadioButton alt = new RadioButton((i + 1) + ") " + alternativas.get(i));
                    int finalI = i;
                    alt.setToggleGroup(grupo);
                    alt.setOnAction(e -> {
                        respostasAluno.put(questao.getNumero(), String.valueOf(finalI));
                        Resposta respostaAluno = obterOuCriarResposta(aplicacaoProva.getAluno());
                        respostaAluno.responderObjetivas(questao.getNumero(), String.valueOf(finalI));
                        aplicacaoProvaService.salvarRespostas(aplicacaoProva, questao.getNumero());
                    });
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

            aplicacaoProvaService.verificarTempo(aplicacaoProva);

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
        Platform.runLater(() -> {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Tempo Esgotado");
            alerta.setHeaderText("A prova foi finalizada automaticamente.");
            alerta.setContentText("O tempo da prova chegou ao fim.");
            alerta.showAndWait();
            encerrarProva();
        });
    }

    private void desabilitarRespostas() {
        for (javafx.scene.Node node : boxQuestoes.getChildren()) {
            if (node instanceof VBox box) {
                for (javafx.scene.Node child : box.getChildren()) {
                    child.setDisable(true); // desativa TextArea ou RadioButton
                }
            }
        }
    }

    private void encerrarProva() {
        if (cronometro != null) cronometro.stop();

        btnFinalizar.setDisable(true);
        labelCronometro.setText("Prova encerrada.");

        desabilitarRespostas(); // o aluno nao pode mais responder
        aplicacaoProvaService.finalizarAplicacao(aplicacaoProva);

        System.out.println("Data e hora de término (aplicacaoProva): " + aplicacaoProva.getDataHoraFim());

        System.out.println("Respostas do aluno:");
        respostasAluno.forEach((numero, resposta) -> {
            System.out.println("Q" + numero + ": " + resposta);
        });

        if (callback != null) {
            callback.onProvaFinalizada(prova);
        }

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Prova Finalizada");
        alerta.setHeaderText("Obrigado!");
        alerta.setContentText("Sua prova foi finalizada.");
        alerta.showAndWait();

        voltarParaMenu();
    }

    private boolean confirmarFinalizacao() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Finalizar Prova");
        alert.setHeaderText("Você deseja finalizar a prova agora?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private Resposta obterOuCriarResposta(Aluno aluno) {
        for (Resposta r : aplicacaoProva.getRespostas()) {
            if (r.getaluno().equals(aluno)) {
                return r;
            }
        }
        Resposta nova = new Resposta(aluno, prova);
        aplicacaoProva.getRespostas().add(nova);
        return nova;
    }

    private void voltarParaMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aluno/TelaDeMenuDeProvas.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnFinalizar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu de Provas");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Não foi possível retornar ao menu.");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }
}
