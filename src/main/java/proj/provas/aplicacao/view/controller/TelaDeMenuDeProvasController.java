package proj.provas.aplicacao.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import proj.provas.aplicacao.controller.ProvaController;
import proj.provas.aplicacao.model.Aluno;
import proj.provas.aplicacao.model.AplicacaoProva;
import proj.provas.aplicacao.model.Prova;
import proj.provas.aplicacao.repository.impl.ProvaRepositoryImpl;
import proj.provas.aplicacao.session.Sessao;
import proj.provas.aplicacao.util.ArquivoUtils; //

import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TelaDeMenuDeProvasController {

    @FXML private TableView<Prova> tabelaProvas;
    @FXML private TableColumn<Prova, String> colunaDisciplina;
    @FXML private TableColumn<Prova, String> colunaDataHora;
    @FXML private TableColumn<Prova, Integer> colunaDuracao;
    @FXML private TableColumn<Prova, Double> colunaNota;
    @FXML private TableColumn<Prova, Void> colunaAcao;
    @FXML Button btnVoltarPrincipalAluno;

    private final ProvaController provaController = new ProvaController(ProvaRepositoryImpl.getInstance());

    private final ObservableList<Prova> provasPendentes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // carrega as provas salvas no provas.dat gerado pelo ArquivoUtils
        List<Prova> todasProvas = ArquivoUtils.carregarProvas();
        System.out.println("Provas carregadas do arquivo: " + todasProvas.size());

        Aluno alunoLogado = (Aluno) Sessao.getInstance().getUsuarioLogado();

        for (Prova prova : todasProvas) {
            System.out.println("Verificando prova: " + prova.getDisciplina().getNome());

            for (AplicacaoProva aplicacao : prova.getAplicacoes()) {
                System.out.println("Aplicação - Aluno: " + aplicacao.getAluno().getNomeCompleto()
                        + ", dataFim: " + aplicacao.getDataHoraFim());

                System.out.println("Aluno logado: " + alunoLogado.getNomeCompleto());
                System.out.println("Comparando com aplicação de: " + aplicacao.getAluno().getNomeCompleto());
                System.out.println("É igual? " + aplicacao.getAluno().equals(alunoLogado));
            }

            boolean alunoJaFez = prova.getAplicacoes().stream()
                    .anyMatch(aplicacao -> aplicacao.getAluno().equals(alunoLogado)
                            && aplicacao.getDataHoraFim() != null);
            if (!alunoJaFez) {
                provasPendentes.add(prova);
            }
        }

        tabelaProvas.setItems(provasPendentes);

        colunaDisciplina.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDisciplina().getNome()));
        colunaDataHora.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getDataAplicacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        colunaDuracao.setCellValueFactory(new PropertyValueFactory<>("duracaoMinutos"));
        colunaNota.setCellValueFactory(new PropertyValueFactory<>("notaTotal"));

        adicionarBotaoFazerProva();
    }

    private void adicionarBotaoFazerProva() {
        colunaAcao.setCellFactory(coluna -> new TableCell<>() {
            private final Button btn = new Button("Fazer Prova");

            {
                btn.setOnAction(event -> {
                    Prova prova = getTableView().getItems().get(getIndex());
                    abrirTelaDeProva(prova);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    private void abrirTelaDeProva(Prova prova) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaAplicarProva.fxml"));
            Parent root = loader.load();

            // carrega a prova
            TelaAplicarProvaController controller = loader.getController();
            controller.carregarProva(prova);

            // remove a prova da lista após ser finalizada
            controller.setProvaFinalizadaListener(provaFinalizada -> {
                provasPendentes.remove(provaFinalizada);
            });

            Stage stage = (Stage) tabelaProvas.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Aplicar Prova - " + prova.getDisciplina().getNome());
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível abrir a prova: " + e.getMessage());
        }
    }
    @FXML
private void voltarTelaPrincipalAluno() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/aluno/TelaPrincipalAluno.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnVoltarPrincipalAluno.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Painel do Aluno");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível voltar para a tela principal: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
