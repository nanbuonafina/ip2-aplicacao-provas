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
import proj.provas.aplicacao.model.Questao;
import proj.provas.aplicacao.model.QuestaoDissertativa;
import proj.provas.aplicacao.model.QuestaoObjetiva;
import proj.provas.aplicacao.repository.impl.QuestaoRepositoryImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaDeQuestoesController {

    @FXML private TableView<Questao> tabelaQuestoes;
    @FXML private TableColumn<Questao, Integer> colunaNumero;
    @FXML private TableColumn<Questao, String> colunaEnunciado;
    @FXML private TableColumn<Questao, Double> colunaValor;
    @FXML private TableColumn<Questao, String> colunaTipo;

    @FXML private TextField campoNumero;
    @FXML private TextField campoEnunciado;
    @FXML private TextField campoValor;
    @FXML private ComboBox<String> comboTipo;

    @FXML private VBox boxAlternativas;
    @FXML private TextField campoAlternativa1;
    @FXML private TextField campoAlternativa2;
    @FXML private TextField campoAlternativa3;
    @FXML private TextField campoAlternativa4;
    @FXML private TextField campoAlternativa5;
    @FXML private TextField campoRespostaCorreta;

    @FXML private Label labelMensagem;

    private final QuestaoController questaoController =
            new QuestaoController(QuestaoRepositoryImpl.getInstance());

    private final ObservableList<Questao> questoes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colunaNumero.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumero()).asObject());
        colunaEnunciado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEnunciado()));
        colunaValor.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getValor()).asObject());
        colunaTipo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTipo()));

        tabelaQuestoes.setItems(questoes);
        atualizarTabela();

        comboTipo.setOnAction(event -> {
            String tipoSelecionado = comboTipo.getValue();
            boxAlternativas.setVisible("Objetiva".equals(tipoSelecionado));
        });

        tabelaQuestoes.setOnMouseClicked(event -> {
            Questao selecionada = tabelaQuestoes.getSelectionModel().getSelectedItem();
            if (selecionada != null) {
                preencherCampos(selecionada);
            }
        });
    }

    @FXML
    private void adicionarOuAtualizar() {
        try {
            int numero = Integer.parseInt(campoNumero.getText());
            String enunciado = campoEnunciado.getText();
            double valor = Double.parseDouble(campoValor.getText());
            String tipo = comboTipo.getValue();

            Questao questao;

            if ("Dissertativa".equals(tipo)) {
                questao = new QuestaoDissertativa(numero, enunciado, valor);
            } else if ("Objetiva".equals(tipo)) {
                List<String> alternativas = new ArrayList<>();
                if (!campoAlternativa1.getText().isBlank()) alternativas.add(campoAlternativa1.getText());
                if (!campoAlternativa2.getText().isBlank()) alternativas.add(campoAlternativa2.getText());
                if (!campoAlternativa3.getText().isBlank()) alternativas.add(campoAlternativa3.getText());
                if (!campoAlternativa4.getText().isBlank()) alternativas.add(campoAlternativa4.getText());
                if (!campoAlternativa5.getText().isBlank()) alternativas.add(campoAlternativa5.getText());

                int respostaCorreta = Integer.parseInt(campoRespostaCorreta.getText()) - 1;

                questao = new QuestaoObjetiva(numero, enunciado, alternativas, respostaCorreta, valor);
            } else {
                labelMensagem.setText("Selecione um tipo de questão.");
                return;
            }

            questaoController.adicionarQuestao(questao);
            atualizarTabela();
            limparCampos();
            labelMensagem.setText("Questão salva com sucesso!");
        } catch (Exception e) {
            labelMensagem.setText("Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void removerQuestao() {
        Questao selecionada = tabelaQuestoes.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            questaoController.removerQuestao(selecionada.getNumero());
            atualizarTabela();
            limparCampos();
            labelMensagem.setText("Questão removida.");
        } else {
            labelMensagem.setText("Selecione uma questão para remover.");
        }
    }

    @FXML
    private void voltarParaPaginaProfessor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) campoNumero.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Menu do Professor");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            labelMensagem.setText("Erro ao voltar para o menu.");
        }
    }

    @FXML
    private void limparCampos() {
        campoNumero.clear();
        campoEnunciado.clear();
        campoValor.clear();
        comboTipo.getSelectionModel().clearSelection();

        campoAlternativa1.clear();
        campoAlternativa2.clear();
        campoAlternativa3.clear();
        campoAlternativa4.clear();
        campoAlternativa5.clear();
        campoRespostaCorreta.clear();

        boxAlternativas.setVisible(false);
        tabelaQuestoes.getSelectionModel().clearSelection();
        labelMensagem.setText("");
    }

    private void preencherCampos(Questao questao) {
        campoNumero.setText(String.valueOf(questao.getNumero()));
        campoEnunciado.setText(questao.getEnunciado());
        campoValor.setText(String.valueOf(questao.getValor()));
        comboTipo.setValue(questao.getTipo());

        if (questao instanceof QuestaoObjetiva objetiva) {
            List<String> alternativas = objetiva.getAlternativas();
            campoAlternativa1.setText(alternativas.size() > 0 ? alternativas.get(0) : "");
            campoAlternativa2.setText(alternativas.size() > 1 ? alternativas.get(1) : "");
            campoAlternativa3.setText(alternativas.size() > 2 ? alternativas.get(2) : "");
            campoAlternativa4.setText(alternativas.size() > 3 ? alternativas.get(3) : "");
            campoAlternativa5.setText(alternativas.size() > 4 ? alternativas.get(4) : "");
            campoRespostaCorreta.setText(String.valueOf(objetiva.getIdRespostaCorreta() + 1));
            boxAlternativas.setVisible(true);
        } else {
            boxAlternativas.setVisible(false);
        }
    }

    private void atualizarTabela() {
        questoes.setAll(questaoController.listarQuestoes());
    }
}
