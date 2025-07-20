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

        // Pega a resposta do aluno, se houver
        Resposta resposta = aplicacaoProva.getRespostas().isEmpty() ? null : aplicacaoProva.getRespostas().get(0);

        for (int i = 0; i < questoes.size(); i++) {
            Questao questao = questoes.get(i);
            int numeroQuestao = i + 1;  // Número da questão começa em 1

            Label labelQuestao = new Label("Questão " + numeroQuestao + ": " + questao.getEnunciado());
            labelQuestao.setStyle("-fx-font-weight: bold; -fx-padding: 5 0 0 0;");
            vboxQuestoes.getChildren().add(labelQuestao);

            if (resposta != null) {
                if (questao instanceof QuestaoObjetiva) {
                    QuestaoObjetiva qo = (QuestaoObjetiva) questao;

                    Label labelAlternativas = new Label("Alternativas:");
                    vboxQuestoes.getChildren().add(labelAlternativas);

                    List<String> alternativas = qo.getAlternativas();

                    Integer respostaAluno = resposta.getRespostaObjetiva(numeroQuestao);
                    int idxRespostaAluno = (respostaAluno != null) ? respostaAluno : -1;

                    for (int j = 0; j < alternativas.size(); j++) {
                        String textoCorreta = (j == qo.getIdRespostaCorreta() - 1) ? " (Correta)" : "";
                        String textoAluno = (j == idxRespostaAluno) ? " <- Resposta do aluno" : "";
                        Label alternativaLabel = new Label(j + ": " + alternativas.get(j) + textoCorreta + textoAluno);
                        vboxQuestoes.getChildren().add(alternativaLabel);
                    }

                } else if (questao instanceof QuestaoDissertativa) {
                    QuestaoDissertativa qd = (QuestaoDissertativa) questao;

                    String respostaAlunoTexto = resposta.getRespostaDissertativa(numeroQuestao);
                    if (respostaAlunoTexto == null) {
                        respostaAlunoTexto = "";
                    }

                    Label respostaAluno = new Label("Resposta do aluno: " + respostaAlunoTexto);
                    vboxQuestoes.getChildren().add(respostaAluno);

                    Label labelNota = new Label("Nota para esta questão:");
                    TextField campoNota = new TextField();

                    Double notaSalva = resposta.getNotaDissertativa(numeroQuestao);
                    if (notaSalva != null) {
                        campoNota.setText(String.valueOf(notaSalva));
                    }

                    campoNota.setPromptText("Digite a nota da questão");
                    vboxQuestoes.getChildren().addAll(labelNota, campoNota);
                    camposNotaDissertativas.put(qd, campoNota);
                }
            } else {
                Label labelSemResposta = new Label("Sem resposta para esta questão.");
                vboxQuestoes.getChildren().add(labelSemResposta);
            }

            Separator separator = new Separator();
            vboxQuestoes.getChildren().add(separator);
        }
    }

    @FXML
    private void salvarNotas() {
        if (aplicacaoProva == null) {
            mostrarAlerta("Nenhuma aplicação de prova carregada.");
            return;
        }

        Resposta resposta = aplicacaoProva.getRespostas().isEmpty() ? null : aplicacaoProva.getRespostas().get(0);
        if (resposta == null) {
            mostrarAlerta("Nenhuma resposta encontrada para esta aplicação.");
            return;
        }

        double notaTotal = 0.0;

        // Corrige questões objetivas automaticamente
        List<Questao> questoes = aplicacaoProva.getProva().getQuestoes();
        for (int i = 0; i < questoes.size(); i++) {
            Questao questao = questoes.get(i);
            int numeroQuestao = i + 1;

            if (questao instanceof QuestaoObjetiva qo) {
                Integer respostaAluno = resposta.getRespostaObjetiva(numeroQuestao);
                if (respostaAluno != null && respostaAluno == qo.getIdRespostaCorreta()) {
                    resposta.getNotasObjetivas().put(numeroQuestao, qo.getValor());
                    notaTotal += qo.getValor();
                } else {
                    resposta.getNotasObjetivas().put(numeroQuestao, 0.0);
                }
            }
        }

        // Corrige questões dissertativas com base na nota inserida
        for (Map.Entry<QuestaoDissertativa, TextField> entry : camposNotaDissertativas.entrySet()) {
            QuestaoDissertativa questao = entry.getKey();
            TextField campo = entry.getValue();
            try {
                double nota = Double.parseDouble(campo.getText());
                int numeroQuestao = aplicacaoProva.getProva().getQuestoes().indexOf(questao) + 1;
                resposta.atribuirNotasDissertativas(numeroQuestao, nota);
                notaTotal += nota;
            } catch (NumberFormatException e) {
                mostrarAlerta("Digite notas válidas em todas as questões dissertativas.");
                return;
            }
        }

        aplicacaoProva.setDissertativasCorrigidas(true);
        resposta.calcularNotaTotal();  // Atualiza o campo interno `nota`

        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Correção salva com sucesso!\nNota final: " + notaTotal);
        alert.showAndWait();

        btnSalvar.setDisable(true);

        Stage stage = (Stage) btnSalvar.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void voltarParaCorrecao() {
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
