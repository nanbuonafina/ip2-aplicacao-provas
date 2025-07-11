package proj.provas.aplicacao.view.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.provas.aplicacao.controller.QuestaoController;
import proj.provas.aplicacao.model.*;
import proj.provas.aplicacao.repository.impl.QuestaoRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelaDeQuestoesController {

    @FXML private TableView<Questao> tabelaQuestoes;
    @FXML private TableColumn<Questao, Integer> colunaNumero;
    @FXML private TableColumn<Questao, String> colunaEnunciado;
    @FXML private TableColumn<Questao, Double> colunaValor;
    @FXML private TableColumn<Questao, String> colunaTipo;

    @FXML private TextField campoNumero, campoEnunciado, campoValor;
    @FXML private ComboBox<String> comboTipo;

    @FXML private VBox boxAlternativas;
    @FXML private TextField campoAlternativa1, campoAlternativa2, campoAlternativa3, campoAlternativa4, campoAlternativa5, campoRespostaCorreta;

    @FXML private Label labelMensagem;

    private final QuestaoController questaoController = new QuestaoController(new QuestaoRepositoryImpl());

    private final ObservableList<Questao> listaQuestoes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colunaNumero.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumero()).asObject());
        colunaEnunciado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEnunciado()));
        colunaValor.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getValor()).asObject());
        colunaTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue() instanceof QuestaoObjetiva ? "Objetiva" : "Dissertativa"));

        adicionarQuestoesExemplo();

        tabelaQuestoes.setItems(listaQuestoes);
        carregarQuestoes();

        comboTipo.setOnAction(e -> {
            boolean objetiva = "Objetiva".equals(comboTipo.getValue());
            boxAlternativas.setVisible(objetiva);
        });

        tabelaQuestoes.getSelectionModel().selectedItemProperty().addListener((obs, old, nova) -> preencherCampos(nova));
    }

    private void carregarQuestoes() {
        listaQuestoes.setAll(questaoController.listarQuestoes());
    }

    @FXML
    private void adicionarOuAtualizar() {
        try {
            int numero = Integer.parseInt(campoNumero.getText());
            String enunciado = campoEnunciado.getText();
            double valor = Double.parseDouble(campoValor.getText());
            String tipo = comboTipo.getValue();

            if (enunciado.isBlank() || tipo == null) {
                mostrarMensagem("Preencha todos os campos obrigatórios.", false);
                return;
            }

            Questao questao;

            if (tipo.equals("Dissertativa")) {
                questao = new QuestaoDissertativa(numero, enunciado, valor);
            } else {
                List<String> alternativas = List.of(
                        campoAlternativa1.getText(),
                        campoAlternativa2.getText(),
                        campoAlternativa3.getText(),
                        campoAlternativa4.getText(),
                        campoAlternativa5.getText()
                );
                int respostaCorreta = Integer.parseInt(campoRespostaCorreta.getText()) - 1;
                questao = new QuestaoObjetiva(numero, enunciado, alternativas, respostaCorreta, valor);
            }

            Optional<Questao> existente = questaoController.listarQuestoes().stream().filter(q -> q.getNumero() == numero).findFirst();
            existente.ifPresent(q -> questaoController.removerQuestao(numero));

            questaoController.adicionarQuestao(questao);
            carregarQuestoes();
            limparCampos();
            mostrarMensagem("Questão salva com sucesso.", true);
        } catch (NumberFormatException e) {
            mostrarMensagem("Número, valor ou índice da resposta inválido.", false);
        } catch (Exception e) {
            mostrarMensagem(e.getMessage(), false);
        }
    }

    @FXML
    private void removerQuestao() {
        Questao selecionada = tabelaQuestoes.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            questaoController.removerQuestao(selecionada.getNumero());
            carregarQuestoes();
            limparCampos();
            mostrarMensagem("Questão removida.", true);
        }
    }

    @FXML
    private void limparCampos() {
        campoNumero.clear();
        campoEnunciado.clear();
        campoValor.clear();
        comboTipo.setValue(null);
        campoAlternativa1.clear();
        campoAlternativa2.clear();
        campoAlternativa3.clear();
        campoAlternativa4.clear();
        campoAlternativa5.clear();
        campoRespostaCorreta.clear();
        boxAlternativas.setVisible(false);
        tabelaQuestoes.getSelectionModel().clearSelection();
        mostrarMensagem("", true);
    }

    private void preencherCampos(Questao questao) {
        if (questao == null) return;

        campoNumero.setText(String.valueOf(questao.getNumero()));
        campoEnunciado.setText(questao.getEnunciado());
        campoValor.setText(String.valueOf(questao.getValor()));

        if (questao instanceof QuestaoObjetiva qObj) {
            comboTipo.setValue("Objetiva");
            boxAlternativas.setVisible(true);
            List<String> alts = qObj.getAlternativas();
            campoAlternativa1.setText(alts.get(0));
            campoAlternativa2.setText(alts.get(1));
            campoAlternativa3.setText(alts.get(2));
            campoAlternativa4.setText(alts.get(3));
            campoAlternativa5.setText(alts.get(4));
            campoRespostaCorreta.setText(String.valueOf(qObj.getIdRespostaCorreta() + 1));
        } else {
            comboTipo.setValue("Dissertativa");
            boxAlternativas.setVisible(false);
        }
    }

    @FXML
    private void voltarParaPaginaProfessor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) tabelaQuestoes.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Página do Professor");

        } catch (Exception e) {
            mostrarAlerta("Erro ao voltar", "Não foi possível voltar para a página do professor.\n" + e.getMessage());
        }
    }

    private void adicionarQuestoesExemplo() {
        if (questaoController.listarQuestoes().isEmpty()) {
            // Questão dissertativa
            QuestaoDissertativa q1 = new QuestaoDissertativa(
                    1,
                    "Explique o conceito de encapsulamento em POO.",
                    2.0
            );

            // Questão objetiva
            QuestaoObjetiva q2 = new QuestaoObjetiva(
                    2,
                    "Qual destas opções representa um laço de repetição em Java?",
                    List.of("if", "while", "switch", "case", "break"),
                    1,  // Resposta correta é "while"
                    1.0
            );

            QuestaoObjetiva q3 = new QuestaoObjetiva(
                    3,
                    "Qual palavra-chave é usada para criar uma classe em Java?",
                    List.of("method", "new", "class", "define", "object"),
                    2,  // "class"
                    1.0
            );

            questaoController.adicionarQuestao(q1);
            questaoController.adicionarQuestao(q2);
            questaoController.adicionarQuestao(q3);
        }
    }



    private void mostrarMensagem(String mensagem, boolean sucesso) {
        labelMensagem.setText(mensagem);
        labelMensagem.setStyle(sucesso ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}