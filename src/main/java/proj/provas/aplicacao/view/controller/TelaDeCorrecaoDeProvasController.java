package proj.provas.aplicacao.view.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import proj.provas.aplicacao.model.AplicacaoProva;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.util.ArquivoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaDeCorrecaoDeProvasController {

    @FXML
    private TableView<AplicacaoProva> tabelaProvasPendentes;

    @FXML
    private TableColumn<AplicacaoProva, String> colunaAluno;

    @FXML
    private TableColumn<AplicacaoProva, String> colunaTurma;

    @FXML
    private TableColumn<AplicacaoProva, Double> colunaNotaFinal;

    @FXML
    private TableColumn<AplicacaoProva, Void> colunaAcao;

    private ObservableList<AplicacaoProva> provasPendentes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colunaAluno.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getAluno().getNomeCompleto());
        });

        colunaTurma.setCellValueFactory(cellData -> {
            var turma = cellData.getValue().getAluno().getTurma();
            String identificacao = (turma != null) ? turma.getIdentificacao() : "Sem Turma";
            return new ReadOnlyStringWrapper(identificacao);
        });

        colunaNotaFinal.setCellValueFactory(new PropertyValueFactory<>("notaFinal"));

        adicionarBotaoCorrigir();
        carregarProvasPendentes();
    }

    private void adicionarBotaoCorrigir() {
        colunaAcao.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Corrigir");

            {
                btn.setOnAction(event -> {
                    AplicacaoProva aplicacao = getTableView().getItems().get(getIndex());
                    abrirTelaCorrecaoDetalhada(aplicacao);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(btn));
                }
            }
        });
    }

    private void carregarProvasPendentes() {
        List<Prova> todasAsProvas = ArquivoUtils.carregarProvas();
        List<AplicacaoProva> pendentes = new ArrayList<>();

        for (Prova prova : todasAsProvas) {
            for (AplicacaoProva aplicacao : prova.getAplicacoes()) {
                if (!aplicacao.isDissertativasCorrigidas()) {
                    pendentes.add(aplicacao);
                }
            }
        }

        provasPendentes.setAll(pendentes);
        tabelaProvasPendentes.setItems(provasPendentes);
    }

    private void abrirTelaCorrecaoDetalhada(AplicacaoProva aplicacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/proj/provas/aplicacao/view/TelaCorrecaoDetalhada.fxml"));
            Scene scene = new Scene(loader.load());

            TelaCorrecaoDetalhadaController controller = loader.getController();
            controller.setAplicacaoProva(aplicacao);

            Stage stage = new Stage();
            stage.setTitle("Correção da Prova");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao abrir tela de correção.");
        }
    }


    @FXML
    private void voltarParaPaginaDoProfessor(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaPaginaProfessor.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) tabelaProvasPendentes.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao voltar para a página do professor.");
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
