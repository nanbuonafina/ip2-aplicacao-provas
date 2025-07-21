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
import javafx.stage.Stage;
import javafx.util.Callback;
import proj.provas.aplicacao.model.AplicacaoProva;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.model.Resposta;
import proj.provas.aplicacao.model.Resultado;
import proj.provas.aplicacao.util.ArquivoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaDeCorrecaoDeProvasController {

    @FXML
    private TableView<Resultado> tabelaProvasPendentes;

    @FXML
    private TableColumn<Resultado, String> colunaAluno;

    @FXML
    private TableColumn<Resultado, String> colunaTurma;

    @FXML
    private TableColumn<Resultado, Double> colunaNotaFinal;

    @FXML
    private TableColumn<Resultado, Void> colunaAcao;

    private List<Prova> listaDeProvas;

    private ObservableList<Resultado> resultadosPendentes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colunaAluno.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getAluno().getNomeCompleto())
        );

        colunaTurma.setCellValueFactory(cellData -> {
            var turma = cellData.getValue().getAluno().getTurma();
            String identificacao = (turma != null) ? turma.getIdentificacao() : "Sem Turma";
            return new ReadOnlyStringWrapper(identificacao);
        });

        colunaNotaFinal.setCellValueFactory(new PropertyValueFactory<>("notaFinal"));

        listaDeProvas = ArquivoUtils.carregarProvas();
        adicionarBotaoCorrigir();
        carregarResultadosPendentes();
    }

    private void adicionarBotaoCorrigir() {
        colunaAcao.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Corrigir");

            {
                btn.setOnAction(event -> {
                    Resultado resultado = getTableView().getItems().get(getIndex());
                    AplicacaoProva aplicacao = resultado.getProva()
                            .getAplicacoes()
                            .stream()
                            .filter(a -> a.getAluno().equals(resultado.getAluno()))
                            .findFirst()
                            .orElse(null);

                    if (aplicacao != null) {
                        abrirTelaCorrecaoDetalhada(aplicacao);
                    } else {
                        mostrarAlerta("Aplicação da prova não encontrada para o aluno.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });

    }

    private void carregarResultadosPendentes() {
        List<Prova> todasAsProvas = ArquivoUtils.carregarProvas();
        List<Resultado> pendentes = new ArrayList<>();

        for (Prova prova : todasAsProvas) {
            for (AplicacaoProva aplicacao : prova.getAplicacoes()) {
                if (!aplicacao.isDissertativasCorrigidas()) {
                    // Obtem a nota da resposta, se já tiver uma
                    double nota = 0.0;
                    for (Resposta resposta : aplicacao.getRespostas()) {
                        nota += resposta.calcularNotaTotal(); // Garante recálculo
                    }

                    Resultado resultado = new Resultado(
                            aplicacao.getAluno(),
                            prova,
                            nota,
                            "Correção pendente"
                    );
                    pendentes.add(resultado);
                }
            }
        }

        resultadosPendentes.setAll(pendentes);
        tabelaProvasPendentes.setItems(resultadosPendentes);
    }



    private void abrirTelaCorrecaoDetalhada(AplicacaoProva aplicacao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/professor/TelaCorrecaoDetalhada.fxml"));
            Scene scene = new Scene(loader.load());

            TelaCorrecaoDetalhadaController controller = loader.getController();
            controller.setAplicacaoProva(aplicacao);
            controller.setListaDeProvasAtualizada(listaDeProvas);

            Stage stage = new Stage();
            stage.setTitle("Correção da Prova");
            stage.setScene(scene);

            // quando a tela for fechada, recarrega a tabela
            stage.setOnHiding(event -> carregarResultadosPendentes());

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
