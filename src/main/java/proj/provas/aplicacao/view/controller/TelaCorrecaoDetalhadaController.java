package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.util.ArquivoUtils;

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

    private List<Prova> listaDeProvasAtualizada;

    private Map<QuestaoDissertativa, TextField> camposNotaDissertativas = new HashMap<>();

    public void setAplicacaoProva(AplicacaoProva aplicacaoProva) {
        this.aplicacaoProva = aplicacaoProva;
        carregarDadosNaTela();
    }

    public void setListaDeProvasAtualizada(List<Prova> lista) {
        this.listaDeProvasAtualizada = lista;
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

        // Pega a resposta do aluno
        Resposta resposta = aplicacaoProva.getRespostas().isEmpty() ? null : aplicacaoProva.getRespostas().get(0);
        if (resposta == null) {
            mostrarAlerta("Nenhuma resposta encontrada para esta aplicação.");
            return;
        }

        List<Questao> questoes = aplicacaoProva.getProva().getQuestoes();

        // === Corrige questões objetivas ===
        for (int i = 0; i < questoes.size(); i++) {
            Questao questao = questoes.get(i);
            int numeroQuestao = i + 1;

            if (questao instanceof QuestaoObjetiva qo) {
                Integer respostaAluno = resposta.getRespostaObjetiva(numeroQuestao);
                if (respostaAluno != null && respostaAluno == qo.getIdRespostaCorreta() - 1) {
                    resposta.getNotasObjetivas().put(numeroQuestao, qo.getValor());
                } else {
                    resposta.getNotasObjetivas().put(numeroQuestao, 0.0);
                }
            }
        }

        // === Corrige questões dissertativas ===
        for (Map.Entry<QuestaoDissertativa, TextField> entry : camposNotaDissertativas.entrySet()) {
            QuestaoDissertativa questao = entry.getKey();
            TextField campo = entry.getValue();

            try {
                double nota = Double.parseDouble(campo.getText());
                int numeroQuestao = questoes.indexOf(questao) + 1;
                resposta.atribuirNotasDissertativas(numeroQuestao, nota);
            } catch (NumberFormatException e) {
                mostrarAlerta("Digite notas válidas em todas as questões dissertativas.");
                return;
            }
        }

        // === Recalcula a nota total da resposta ===
        resposta.calcularNotaTotal();

        // === ATUALIZA a nota final da aplicação (ponto crítico) ===
        aplicacaoProva.setNotaFinal(resposta.getNotaTotal());
        aplicacaoProva.setDissertativasCorrigidas(true);


        for (Prova p : listaDeProvasAtualizada) {
            if (p.getId().equals(aplicacaoProva.getProva().getId())) {
                List<AplicacaoProva> aplicacoes = p.getAplicacoes();
                for (int i = 0; i < aplicacoes.size(); i++) {
                    AplicacaoProva ap = aplicacoes.get(i);
                    if (ap.getId().equals(aplicacaoProva.getId())) {
                        aplicacoes.set(i, aplicacaoProva);  // substitui completamente a aplicação atualizada
                        break;
                    }
                }
                break;
            }
        }

        // === Diagnóstico detalhado ===
        double somaObjetivas = resposta.getNotasObjetivas().values().stream().mapToDouble(Double::doubleValue).sum();
        double somaDissertativas = resposta.getNotasDissertativas().values().stream().mapToDouble(d -> d).sum();
        double notaFinal = resposta.getNotaTotal();

        System.out.println("=== DIAGNÓSTICO FINAL ===");
        System.out.printf("Nota objetiva total: %.2f%n", somaObjetivas);
        System.out.printf("Nota dissertativa total: %.2f%n", somaDissertativas);
        System.out.printf("Nota final (Resposta): %.2f%n", notaFinal);
        System.out.printf("Nota final (AplicacaoProva): %.2f%n", aplicacaoProva.getNotaFinal());

        // === Persiste a lista de provas ===
        ArquivoUtils.salvarTodasProvas(listaDeProvasAtualizada);
        System.out.println("Depois de salvar.");

        // === Mostra confirmação para o usuario ===
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Correção salva com sucesso!\nNota final: " + String.format("%.2f", notaFinal));
        alert.showAndWait();

        // === Fecha a janela de correção ===
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
